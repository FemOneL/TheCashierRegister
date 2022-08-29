package com.epam.cashierregister.services.DAO;

import com.epam.cashierregister.services.DAO.connection.DBHandler;
import com.epam.cashierregister.services.DAO.connection.TestDbHandler;
import com.epam.cashierregister.services.entities.goods.Category;
import com.epam.cashierregister.services.entities.goods.Goods;
import com.epam.cashierregister.services.entities.goods.Producer;
import com.epam.cashierregister.services.exeptions.GoodsExistInCheckException;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

class GoodsDAOTest {
    private static DBHandler testDBHandler;
    private static GoodsDAO goodsDAO;
    private static MockedStatic<DBHandler> mockStat;


    @BeforeAll
    static void beforeAll() {
        mockStat = mockStatic(DBHandler.class);
    }

    @AfterAll
    static void afterAll() {
        mockStat.close();
        testDBHandler = null;
        goodsDAO = null;
    }

    @BeforeEach
    void setUp() {
        testDBHandler = new TestDbHandler();
        try (Connection connection = testDBHandler.getConnection()) {
            ScriptRunner sr = new ScriptRunner(connection);
            Reader reader = new BufferedReader(new FileReader("src/test/java/com/epam/cashierregister/services/DAO/scriptsForTest/cashregisterScript.sql"));
            sr.runScript(reader);
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void searchGood() {
        Goods expectedGoods = new Goods(1, "iphone 13", "goodsPhotos/nopicture.png", 24,
                new BigDecimal("500.34"), new Category("Phones"), new Producer("xiaomi"));
        when(DBHandler.getConBuilder()).thenReturn(testDBHandler);
        goodsDAO = new GoodsDAO();
        Goods actualGoods = goodsDAO.searchGood("iphone 13", false);
        assertEquals(expectedGoods, actualGoods);
        actualGoods = goodsDAO.searchGood("1", false);
        assertEquals(expectedGoods, actualGoods);
    }

    @Test
    void testDeleteGoods() throws GoodsExistInCheckException {
        int id = 1;
        when(DBHandler.getConBuilder()).thenReturn(testDBHandler);
        goodsDAO = new GoodsDAO();
        goodsDAO.deleteGoods(id);
        Assertions.assertTrue(checkIsGoodsDeleted(id));
    }

    @Test
    void testCheckModel() {
        when(DBHandler.getConBuilder()).thenReturn(testDBHandler);
        goodsDAO = new GoodsDAO();
        Assertions.assertFalse(goodsDAO.checkModel("iphone 13"));
    }

    @Test
    void testUpdateNumber() {
        when(DBHandler.getConBuilder()).thenReturn(testDBHandler);
        goodsDAO = new GoodsDAO();
        Assertions.assertEquals(24, getNumber("iphone 13"));
        goodsDAO.updateNumber(1, 20);
        Assertions.assertEquals(20, getNumber("iphone 13"));
    }

    @Test
    void testAddGoods() {
        Goods goods = new Goods(0, "p40 pro", "images/test.png", 12, new BigDecimal(123), new Category("Computers"), new Producer("xiaomi"));
        when(DBHandler.getConBuilder()).thenReturn(testDBHandler);
        goodsDAO = new GoodsDAO();
        goodsDAO.addGoods(goods);
        Assertions.assertTrue(checkIsGoodsAdded(goods));
    }

    @Test
    void testGetGoods() {
        List<Goods> producerList;
        when(DBHandler.getConBuilder()).thenReturn(testDBHandler);
        goodsDAO = new GoodsDAO();
        producerList = goodsDAO.getGoods(0, null);
        Assertions.assertEquals(2, producerList.size());
        producerList = goodsDAO.getGoods(0, "ipho");
        Assertions.assertEquals(1, producerList.size());
    }

    public boolean checkIsGoodsDeleted(int id){
        try (Connection connection = testDBHandler.getConnection()) {
            String selectProducer = "SELECT * FROM goods WHERE goods_id = ?";
            PreparedStatement statement = connection.prepareStatement(selectProducer);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            return !rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean checkIsGoodsAdded(Goods goods){
        try (Connection connection = testDBHandler.getConnection()) {
            String selectProducer = "SELECT * FROM goods WHERE model = ?";
            PreparedStatement statement = connection.prepareStatement(selectProducer);
            statement.setString(1, goods.getModel());
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public int getNumber(String model){
        try (Connection connection = testDBHandler.getConnection()) {
            String selectProducer = "SELECT numbers FROM goods WHERE model = ?";
            PreparedStatement statement = connection.prepareStatement(selectProducer);
            statement.setString(1, model);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}