package com.epam.cashierregister.controllers.servlets.viewServlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

/**
 * Servlet for cabinet page view
 */
@WebServlet(name = "cabinetServlet", value = "/cabinet")
public class CabinetServlet extends HttpServlet {
    static Logger LOG = LogManager.getLogger(CabinetServlet.class);
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOG.info("Opened cabinet servlet (/cabinet)");
        req.getSession().removeAttribute("error");
        LOG.info("Opened cabinet page (cabinet.jsp)");
        req.getRequestDispatcher("WEB-INF/view/cabinet.jsp").forward(req, resp);
    }
}