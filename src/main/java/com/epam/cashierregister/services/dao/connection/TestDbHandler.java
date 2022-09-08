package com.epam.cashierregister.services.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Test Database handler which used standard connections and test configs
 */
public class TestDbHandler implements DBHandler{
    private Connection connection;

    @Override
    public Connection getConnection() throws SQLException {
        String connectionString ="jdbc:mysql://localhost:3306/cash_register_test";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(connectionString, "root", "nfhfc123");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
