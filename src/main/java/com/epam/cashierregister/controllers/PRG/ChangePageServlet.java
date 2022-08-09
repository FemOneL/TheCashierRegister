package com.epam.cashierregister.controllers.PRG;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ChangePageServlet", value = "/changePage")
public class ChangePageServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int page;
        int currentPage = (Integer) session.getAttribute("page");
        page = getPage(req.getParameter("page"), currentPage);
        if (page < 0) {
            throw new IOException();
        } else {
            session.setAttribute("page", page);
        }
        resp.sendRedirect("goods");
    }

    private int getPage(String page, int currentPage) {
        switch (page) {
            case "first":
                return 0;
            case "right":
                return currentPage + 4;
            case "left":
                if (currentPage == 0) {
                    return currentPage;
                } else {
                    return currentPage - 4;
                }
            default:
                return -1;
        }
    }
}
