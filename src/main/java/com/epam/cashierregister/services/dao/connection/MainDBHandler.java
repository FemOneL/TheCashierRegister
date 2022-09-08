package com.epam.cashierregister.services.dao.connection;

import com.epam.cashierregister.services.exeptions.DatabaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Main Database handler which used connection pool and configs from <code>context.xml</code>
 */
public class MainDBHandler implements DBHandler {
    private final DataSource dataSource;
    static Logger LOG = LogManager.getLogger(MainDBHandler.class);

    public MainDBHandler() throws DatabaseException {
        try {
            Context context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/cashierRegister");
        } catch (NamingException e) {
            LOG.fatal("Database fall");
            throw new DatabaseException(500);
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
