package com.epam.cashierregister.controllers;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "indexServlet", value = "/cabinet")
public class IndexServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        String loc = (String) session.getAttribute("loc");
        if (loc == null){
            loc = "eng";
        }
        session.setAttribute("loc", loc);
        req.getRequestDispatcher("WEB-INF/view/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String login = req.getParameter("login");
        if (login != null){
            session.setAttribute("login", login);
        }
        String lang = req.getParameter("lang");
        if (lang != null) {
            session.setAttribute("loc", lang);
        }
        doGet(req, resp);
    }
}