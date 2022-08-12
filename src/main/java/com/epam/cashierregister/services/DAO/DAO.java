package com.epam.cashierregister.services.DAO;

import com.epam.cashierregister.services.connection.PoolConnectionBuilder;

import java.sql.Connection;
import java.sql.SQLException;

public class DAO {
    protected final PoolConnectionBuilder connectionBuilder;

    public DAO() {
        connectionBuilder = new PoolConnectionBuilder();
    }

    protected Connection getConnection() throws SQLException {
        return connectionBuilder.getConnection();
    }
}
