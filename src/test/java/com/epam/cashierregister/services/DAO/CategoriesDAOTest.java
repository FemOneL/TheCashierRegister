package com.epam.cashierregister.services.DAO;

import com.epam.cashierregister.services.DAO.connection.DBHandler;
import com.epam.cashierregister.services.DAO.connection.TestDbHandler;
import com.epam.cashierregister.services.entities.goods.Category;
import com.epam.cashierregister.services.entities.goods.Producer;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

class CategoriesDAOTest {
    private static DBHandler testDBHandler;
    private static CategoriesDAO categoriesDAO;
    private static MockedStatic<DBHandler> mockStat;

    @BeforeAll
    static void beforeAll() {
        mockStat = mockStatic(DBHandler.class);
    }

    @AfterAll
    static void afterAll() {
        mockStat.close();
        testDBHandler = null;
        categoriesDAO = null;
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
    void addNewCategory() {
        Category category = new Category("AI");
        when(DBHandler.getConBuilder()).thenReturn(testDBHandler);
        categoriesDAO = new CategoriesDAO();
        categoriesDAO.addNewCategory(category);
        Assertions.assertTrue(checkNewCategory(category.getCategory()));
    }

    @Test
    void getCategoryList() {
        List<Category> categoryList;
        when(DBHandler.getConBuilder()).thenReturn(testDBHandler);
        categoriesDAO = new CategoriesDAO();
        categoryList = categoriesDAO.getCategoryList();
        Assertions.assertEquals(3, categoryList.size());
    }

    public boolean checkNewCategory(String category){
        try (Connection connection = testDBHandler.getConnection()) {
            String selectProducer = "SELECT * FROM category WHERE category = ?";
            PreparedStatement statement = connection.prepareStatement(selectProducer);
            statement.setString(1, category);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}