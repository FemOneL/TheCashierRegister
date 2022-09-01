package com.epam.cashierregister.controllers.servlets.frontController.commands;

import com.epam.cashierregister.controllers.servlets.frontController.FrontCommand;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Command for searching
 */
public class SearchCommand extends FrontCommand {
    @Override
    public void initContext() throws ServletException {

    }

    @Override
    public boolean filter() throws ServletException, IOException {
        return true;
    }

    @Override
    public void process() throws ServletException, IOException {
        LOG.info("searching...");
        req.getSession().setAttribute("search", req.getParameter("search"));
        redirect(req.getParameter("view"));
    }
}
