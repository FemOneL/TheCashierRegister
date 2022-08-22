package com.epam.cashierregister.services.DAO;

import com.epam.cashierregister.services.connection.DBHandler;

import java.sql.Connection;
import java.sql.SQLException;

public class DAO {
    protected final DBHandler connectionBuilder;

    public DAO() {
        connectionBuilder = new DBHandler();
    }

    protected Connection getConnection() throws SQLException {
        return connectionBuilder.getConnection();
    }
}
