package com.epam.cashierregister.services.dao;

import com.epam.cashierregister.services.dao.connection.DBHandler;
import com.epam.cashierregister.services.exeptions.DatabaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Data Access Object
 */
public class DAO {
    protected static Logger LOG = LogManager.getLogger(DAO.class);
    protected final DBHandler connectionBuilder;

    protected DAO() throws DatabaseException {
        connectionBuilder = DBHandler.getConBuilder();
    }

    /**
     * @return database connection
     */
    protected Connection getConnection() throws SQLException {
        return connectionBuilder.getConnection();
    }
}
