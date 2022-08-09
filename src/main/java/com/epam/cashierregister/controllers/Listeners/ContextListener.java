package com.epam.cashierregister.controllers.Listeners;

import com.epam.cashierregister.services.ValidateService;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import com.mysql.cj.jdbc.*;

@WebListener
public class ContextListener implements ServletContextListener {

    public ContextListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
        ValidateService validateService = new ValidateService();
        sce.getServletContext().setAttribute("validateService", validateService);
    }

    public void contextDestroyed(ServletContextEvent sce) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        Driver d = null;

        while (drivers.hasMoreElements()) {
            try {
                d = drivers.nextElement();
                DriverManager.deregisterDriver(d);
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        AbandonedConnectionCleanupThread.checkedShutdown();
    }
}
