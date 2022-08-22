package com.epam.cashierregister.services.DAO.connection;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBHandler {
    private MysqlDataSource dataSource;
    private String host;
    private String port;
    private String user;
    private String password;
    private String dbName;

    private void loadConfigs(){
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream("C:\\Users\\tfemy\\Desktop\\Final project\\TheCashierRegister\\src\\main\\resources\\config.properties")) {
            prop.load(input);
            host = prop.getProperty("db.host");
            port = prop.getProperty("db.port");
            user = prop.getProperty("db.user");
            password = prop.getProperty("db.password");
            dbName = prop.getProperty("db.dbName");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public DBHandler(){
        loadConfigs();
        dataSource = new MysqlDataSource();
        dataSource.setServerName(host);
        dataSource.setPort(Integer.parseInt(port));
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setDatabaseName(dbName);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
