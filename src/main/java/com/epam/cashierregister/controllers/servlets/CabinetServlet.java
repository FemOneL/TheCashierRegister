package com.epam.cashierregister.controllers.servlets;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "cabinetServlet", value = "/cabinet")
public class CabinetServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        session.removeAttribute("error");
        req.getRequestDispatcher("WEB-INF/view/cabinet.jsp").forward(req, resp);
    }
}