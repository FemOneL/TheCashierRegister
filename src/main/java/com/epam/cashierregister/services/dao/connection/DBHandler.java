package com.epam.cashierregister.services.dao.connection;

import com.epam.cashierregister.services.exeptions.DatabaseException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface for Database handling
 */
public interface DBHandler {

    /**
     * @return database connection
     */
    Connection getConnection() throws SQLException;

    /**
     * @return handler with connection which we choose
     */
    static DBHandler getConBuilder() throws DatabaseException {
        return new MainDBHandler();
    }
}
