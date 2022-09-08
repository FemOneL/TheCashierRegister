package com.epam.cashierregister.services.dao;

import com.epam.cashierregister.services.dao.connection.DBHandler;
import com.epam.cashierregister.services.dao.connection.TestDbHandler;
import com.epam.cashierregister.services.entities.goods.Producer;
import com.epam.cashierregister.services.exeptions.DatabaseException;
import com.epam.cashierregister.services.exeptions.InvalidInputException;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.mockito.Mockito.*;

class ProducersDAOTest {
    private static DBHandler testDBHandler;
    private static ProducersDAO producersDAO;
    private static MockedStatic<DBHandler> mockStat;

    @BeforeAll
    static void beforeAll() {
       mockStat = mockStatic(DBHandler.class);
    }

    @AfterAll
    static void afterAll() {
        mockStat.close();
        testDBHandler = null;
        producersDAO = null;
    }

    @BeforeEach
    void setUp() {
        testDBHandler = new TestDbHandler();
        try (Connection connection = testDBHandler.getConnection()) {
            ScriptRunner sr = new ScriptRunner(connection);
            Reader reader = new BufferedReader(new FileReader("src/test/java/com/epam/cashierregister/services/DAO/scriptsForTest/cashregisterScript.sql"));
            sr.runScript(reader);
        } catch (SQLException | FileNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void testAddNewProducer() throws SQLException, DatabaseException, InvalidInputException {
        Producer producer = new Producer("apple");
        when(DBHandler.getConBuilder()).thenReturn(testDBHandler);
        producersDAO = new ProducersDAO();
        producersDAO.addNewProducer(producer);
        Assertions.assertTrue(checkNewProducer(producer.getName()));
    }

    @Test
    void testGetProducerList() throws DatabaseException {
        List<Producer> producerList;
        when(DBHandler.getConBuilder()).thenReturn(testDBHandler);
        producersDAO = new ProducersDAO();
        producerList = producersDAO.getProducerList();
        Assertions.assertEquals(3, producerList.size());
    }

    public boolean checkNewProducer(String producerName){
        try (Connection connection = testDBHandler.getConnection()) {
            String selectProducer = "SELECT * FROM producer WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(selectProducer);
            statement.setString(1, producerName);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}