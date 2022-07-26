package com.epam.cashierregister.controllers.servlets.frontcontroller.commands;

import com.epam.cashierregister.controllers.servlets.frontcontroller.FrontCommand;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Unknown command
 */
public class UnknownCommand extends FrontCommand {
    @Override
    public void initContext() throws ServletException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean filter() throws ServletException, IOException {
        return true;
    }

    @Override
    public void process() throws ServletException, IOException {
        req.getSession().setAttribute("javax.servlet.error.status_code", 500);
        redirect("errorPage");
    }
}
