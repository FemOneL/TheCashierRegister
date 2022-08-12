package com.epam.cashierregister.controllers.Listeners;

import com.epam.cashierregister.services.DAO.*;
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
        sce.getServletContext().setAttribute("GoodsDAO", new GoodsDAO());
        sce.getServletContext().setAttribute("ChecksDAO", new ChecksDAO());
        sce.getServletContext().setAttribute("CategoriesDAO", new CategoriesDAO());
        sce.getServletContext().setAttribute("EmployeeDAO", new EmployeeDAO());
        sce.getServletContext().setAttribute("ProducersDAO", new ProducersDAO());
        sce.getServletContext().setAttribute("validateService", new ValidateService());
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
