package com.epam.cashierregister.controllers;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SignInServlet", value = "/signin")
public class SignInServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("login") != null){
            resp.sendRedirect("/cashier/cabinet");
        } else {
            String loc = (String) session.getAttribute("loc");
            if (loc == null) {
                loc = "eng";
            }
            session.setAttribute("loc", loc);
            req.getRequestDispatcher("WEB-INF/view/authorization.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.invalidate();
        doGet(req, resp);
    }
}
