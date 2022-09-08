package com.epam.cashierregister.controllers.listeners;

import com.epam.cashierregister.controllers.servlets.viewservlets.AuthorizeServlet;
import com.epam.cashierregister.services.dao.*;
import com.epam.cashierregister.services.ReportService;
import com.epam.cashierregister.services.ValidateService;
import com.epam.cashierregister.services.exeptions.DatabaseException;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Listener for initialize services
 */
@WebListener
public class ContextListener implements ServletContextListener {
    static Logger LOG = LogManager.getLogger(AuthorizeServlet.class);

    public ContextListener() {}

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOG.info("Web application is deployed");
        try {
            sce.getServletContext().setAttribute("GoodsDAO", new GoodsDAO());
            sce.getServletContext().setAttribute("ChecksDAO", new ChecksDAO());
            sce.getServletContext().setAttribute("CategoriesDAO", new CategoriesDAO());
            sce.getServletContext().setAttribute("EmployeeDAO", new EmployeeDAO());
            sce.getServletContext().setAttribute("ProducersDAO", new ProducersDAO());
            sce.getServletContext().setAttribute("ReportDAO", new ReportDAO());
            sce.getServletContext().setAttribute("validateService", new ValidateService());
            sce.getServletContext().setAttribute("report", new ReportService());
        } catch (DatabaseException e) {
            LOG.fatal("Database error: {}", e.getErrorCode());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOG.info("Web application disconnected");
        AbandonedConnectionCleanupThread.checkedShutdown();
    }
}
