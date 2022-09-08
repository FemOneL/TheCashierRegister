package com.epam.cashierregister.controllers.servlets.frontcontroller;

import com.epam.cashierregister.services.exeptions.DatabaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Abstract class for execute needed commands
 */
public abstract class FrontCommand {
    protected static Logger LOG = LogManager.getLogger(FrontCommand.class);
    protected ServletContext context;
    protected HttpServletRequest req;
    protected HttpServletResponse resp;

    /**
     * initialize request, response and servlet context
     */
    public void initCommand(ServletContext servletContext, HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        this.context = servletContext;
        this.req = servletRequest;
        this.resp = servletResponse;
        initContext();
    }

    /**
     * get services from context
     */
    public abstract void initContext() throws ServletException;

    /**
     * Calls before process
     */
    public abstract boolean filter() throws ServletException, IOException, DatabaseException;

    /**
     * execute command
     */
    public abstract void process() throws ServletException, IOException;

    /**
     * sending redirect in target
     */
    protected void redirect(String target) throws IOException {
        resp.sendRedirect(target);
    }
}
