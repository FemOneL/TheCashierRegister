package com.epam.cashierregister.controllers.servlets.viewServlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AuthorizeServlet", value = "/authorize")
public class AuthorizeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("error") == null) {
            session.setAttribute("error", " ");
        }
        req.getRequestDispatcher("WEB-INF/view/authorization.jsp").forward(req, resp);
    }

}
