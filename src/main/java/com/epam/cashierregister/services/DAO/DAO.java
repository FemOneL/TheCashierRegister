package com.epam.cashierregister.services.DAO;

import com.epam.cashierregister.services.DAO.connection.DBHandler;
import com.epam.cashierregister.services.DAO.connection.MainDBHandler;

import java.sql.Connection;
import java.sql.SQLException;

public class DAO {
    protected final DBHandler connectionBuilder;

    public DAO() {
        connectionBuilder = DBHandler.getConBuilder();
    }

    protected Connection getConnection() throws SQLException {
        return connectionBuilder.getConnection();
    }
}
