package com.epam.cashierregister.controllers.servlets.viewServlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * Servlet for check page view
 */
@WebServlet(name = "CheckServlet", value = "/check")
public class CheckServlet extends HttpServlet {
    static Logger LOG = LogManager.getLogger(CheckServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Opened check servlet (/check)");
        LOG.info("Opened check page (check.jsp)");
        req.getRequestDispatcher("WEB-INF/view/check.jsp").forward(req, resp);
    }
}
