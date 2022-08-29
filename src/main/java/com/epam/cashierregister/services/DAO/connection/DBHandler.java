package com.epam.cashierregister.services.DAO.connection;

import java.sql.Connection;
import java.sql.SQLException;

public interface DBHandler {
    Connection getConnection() throws SQLException;

    static DBHandler getConBuilder(){
        return new MainDBHandler();
    }
}
