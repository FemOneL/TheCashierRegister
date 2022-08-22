package com.epam.cashierregister.controllers.servlets.frontController.commands;

import com.epam.cashierregister.controllers.servlets.frontController.FrontCommand;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class ChangePageCommand extends FrontCommand {

    @Override
    public void initContext() throws ServletException {

    }

    @Override
    public boolean filter() throws ServletException, IOException { return true; }

    @Override
    public void process() throws ServletException, IOException {
        HttpSession session = req.getSession();
        String pageName = req.getParameter("pageName");
        int size = Integer.parseInt(req.getParameter("size"));
        int page;
        int currentPage = (Integer) session.getAttribute("page");
        page = getPage(req.getParameter("page"), currentPage, size);
        if (page < 0) {
            throw new IOException();
        } else {
            session.setAttribute("page", page);
        }
        redirect(pageName);
    }

    private int getPage(String page, int currentPage, int size) {
        switch (page) {
            case "first":
                return 0;
            case "right":
                return currentPage + size;
            case "left":
                if (currentPage == 0) {
                    return currentPage;
                } else {
                    return currentPage - size;
                }
            default:
                return -1;
        }
    }
}
