package com.epam.cashierregister.services.DAO.connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class MainDBHandler implements DBHandler {
    private DataSource dataSource;

    @Override
    public Connection getConnection() throws SQLException {
        try {
            Context context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/cashierRegister");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return dataSource.getConnection();
    }
}
