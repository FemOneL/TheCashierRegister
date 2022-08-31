package com.epam.cashierregister.services.DAO.connection;

import com.epam.cashierregister.services.exeptions.DatabaseException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface for Database handling
 */
public interface DBHandler {

    /**
     * @return database connection
     * @throws SQLException
     */
    Connection getConnection() throws SQLException;

    /**
     * @return handler with connection which we choose
     * @throws DatabaseException
     */
    static DBHandler getConBuilder() throws DatabaseException {
        return new MainDBHandler();
    }
}
