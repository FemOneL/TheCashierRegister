package com.epam.cashierregister.controllers.servlets.frontController;

import com.epam.cashierregister.services.exeptions.DatabaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class FrontCommand {
    protected static Logger LOG = LogManager.getLogger(FrontCommand.class);
    protected ServletContext context;
    protected HttpServletRequest req;
    protected HttpServletResponse resp;

    public void initCommand(ServletContext servletContext, HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        this.context = servletContext;
        this.req = servletRequest;
        this.resp = servletResponse;
        initContext();
    }

    public abstract void initContext() throws ServletException;

    public abstract boolean filter() throws ServletException, IOException, DatabaseException;

    public abstract void process() throws ServletException, IOException;

    protected void redirect(String target) throws ServletException, IOException {
        resp.sendRedirect(target);
    }
}
