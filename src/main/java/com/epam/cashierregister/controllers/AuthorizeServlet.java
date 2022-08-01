package com.epam.cashierregister.controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;

@WebServlet(name = "AuthorizeServlet", value = "/")
public class AuthorizeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("error") == null){
            session.setAttribute("error", " ");
        }
        if (session.getAttribute("loc") == null){
            session.setAttribute("loc", "eng");;
        }
        req.getRequestDispatcher("WEB-INF/view/authorization.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.setAttribute("tempLogin", req.getParameter("login"));
        session.setAttribute("tempPassword", req.getParameter("password"));
        resp.sendRedirect("cabinet");
    }

}
