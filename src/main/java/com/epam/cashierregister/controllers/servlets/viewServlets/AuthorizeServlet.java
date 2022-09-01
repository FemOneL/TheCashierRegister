package com.epam.cashierregister.controllers.servlets.viewServlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for authorize page view
 */
@WebServlet(name = "AuthorizeServlet", value = "/authorize")
public class AuthorizeServlet extends HttpServlet {
    static Logger LOG = LogManager.getLogger(AuthorizeServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Opened authorize servlet (/authorize)");
        LOG.info("Opened authorize page (authorization.jsp)");
        req.getRequestDispatcher("WEB-INF/view/authorization.jsp").forward(req, resp);
    }

}
