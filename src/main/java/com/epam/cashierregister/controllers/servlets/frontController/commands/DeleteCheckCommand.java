package com.epam.cashierregister.controllers.servlets.frontController.commands;

import com.epam.cashierregister.controllers.servlets.frontController.FrontCommand;
import com.epam.cashierregister.services.DAO.ChecksDAO;
import com.epam.cashierregister.services.entities.check.Check;

import javax.servlet.*;
import java.io.IOException;

public class DeleteCheckCommand extends FrontCommand {
    private ChecksDAO checksDAO;

    @Override
    public void initContext() throws ServletException {
        checksDAO = (ChecksDAO) context.getAttribute("ChecksDAO");
    }

    @Override
    public boolean filter() throws ServletException, IOException {return true;}

    @Override
    public void process() throws ServletException, IOException {
        Check check = (Check) req.getSession().getAttribute("activeCheck");
        if (req.getParameter("delete") == null){
            checksDAO.deleteCheck(check);
        } else {

        }
        redirect("checks");
    }
}
