package com.epam.cashierregister.services.DAO;

import com.epam.cashierregister.services.DAO.connection.DBHandler;
import com.epam.cashierregister.services.DAO.connection.MainDBHandler;
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

    public DAO() throws DatabaseException {
        connectionBuilder = DBHandler.getConBuilder();
    }

    /**
     * @return database connection
     * @throws SQLException
     */
    protected Connection getConnection() throws SQLException {
        return connectionBuilder.getConnection();
    }
}
