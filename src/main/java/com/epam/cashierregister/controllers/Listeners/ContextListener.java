package com.epam.cashierregister.controllers.Listeners;

import com.epam.cashierregister.services.DAO.*;
import com.epam.cashierregister.services.ReportService;
import com.epam.cashierregister.services.entities.report.Report;
import com.epam.cashierregister.services.validateServices.ValidateService;

import javax.servlet.*;
import javax.servlet.annotation.*;

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
        sce.getServletContext().setAttribute("ReportDAO", new ReportDAO());
        sce.getServletContext().setAttribute("validateService", new ValidateService());
        sce.getServletContext().setAttribute("report", new ReportService());
    }

    public void contextDestroyed(ServletContextEvent sce) {
        AbandonedConnectionCleanupThread.checkedShutdown();
    }
}
