package com.epam.cashierregister.controllers;

import com.epam.cashierregister.model.Language;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "indexServlet", value = "/")
public class IndexServlet extends HttpServlet {
    public static Language language;

    @Override
    public void init() throws ServletException {
        language = Language.eng;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("loc", language.name());
        request.getRequestDispatcher("WEB-INF/view/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lang = req.getParameter("lang");
        if (lang.equals("ua")) {
            language = Language.ua;
        } else {
            language = Language.eng;
        }
        doGet(req, resp);
    }
}