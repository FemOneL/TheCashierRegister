package com.epam.cashierregister.controllers;

import java.io.*;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "indexServlet", value = "/")
public class IndexServlet extends HttpServlet {
    private static String language;

    @Override
    public void init() throws ServletException {
        language = "eng";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("loc", language);
        request.getRequestDispatcher("WEB-INF/view/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lang = req.getParameter("lang");
        if (lang.equals("ua")) {
            language = "ua";
        } else {
            language = "eng";
        }
        doGet(req, resp);
    }
}