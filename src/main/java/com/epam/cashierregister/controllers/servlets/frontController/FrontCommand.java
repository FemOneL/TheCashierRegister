package com.epam.cashierregister.controllers.servlets.frontController;

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
     * @param servletContext
     * @param servletRequest
     * @param servletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void initCommand(ServletContext servletContext, HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        this.context = servletContext;
        this.req = servletRequest;
        this.resp = servletResponse;
        initContext();
    }

    /**
     * get services from context
     * @throws ServletException
     */
    public abstract void initContext() throws ServletException;

    /**
     * Calls before process
     * @throws ServletException
     * @throws IOException
     * @throws DatabaseException
     */
    public abstract boolean filter() throws ServletException, IOException, DatabaseException;

    /**
     * execute command
     * @throws ServletException
     * @throws IOException
     */
    public abstract void process() throws ServletException, IOException;

    /**
     * sending redirect in target
     * @param target
     * @throws ServletException
     * @throws IOException
     */
    protected void redirect(String target) throws ServletException, IOException {
        resp.sendRedirect(target);
    }
}
