package com.epam.cashierregister.controllers;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "indexServlet", value = "/cabinet")
public class IndexServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        session.removeAttribute("error");
        req.getRequestDispatcher("WEB-INF/view/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("lang") != null){
            resp.sendRedirect("cabinetloc?lang=" + req.getParameter("lang"));
        }else{
            doGet(req, resp);
        }
    }
}