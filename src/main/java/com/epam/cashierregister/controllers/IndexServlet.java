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
        language = "ENGlanguage";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Properties prop = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(language + ".properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find ");
                return;
            }
            prop.load(input);
        }catch (IOException exception){
            System.out.println("error");
            throw new IOException();
        }
        request.setAttribute("language", prop);
        request.getRequestDispatcher("WEB-INF/view/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lang = req.getParameter("lang");
        if (lang.equals("ua")) {
            language = "UAlanguage";
        } else {
            language = "ENGlanguage";
        }
        doGet(req, resp);
    }
}