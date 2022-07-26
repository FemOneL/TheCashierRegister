package com.epam.cashierregister.controllers.servlets.frontcontroller.commands;

import com.epam.cashierregister.controllers.servlets.frontcontroller.FrontCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Command for choose payment type
 */
public class ChoosePaymentTypeCommand extends FrontCommand {

    @Override
    public void initContext() throws ServletException {

    }

    @Override
    public boolean filter() throws ServletException, IOException { return true;}

    @Override
    public void process() throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.setAttribute("type", req.getParameter("type"));
        redirect("pay");
    }
}
