package com.epam.cashierregister.controllers.servlets.frontController.commands;

import com.epam.cashierregister.controllers.servlets.frontController.FrontCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class ChangeLangCommand extends FrontCommand {

    @Override
    public void initContext() throws ServletException {}

    @Override
    public boolean filter() throws ServletException, IOException {
        return true;
    }

    @Override
    public void process() throws ServletException, IOException {
        if (req.getParameter("lang") != null) {
            HttpSession session = req.getSession();
            session.setAttribute("loc", req.getParameter("lang"));
        }
        redirect("cabinet");
    }
}
