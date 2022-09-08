package com.epam.cashierregister.controllers.servlets.viewservlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for error page view
 */
@WebServlet(name = "ErrorPageServlet", value = "/errorPage")
public class ErrorPageServlet extends HttpServlet {
    static Logger LOG = LogManager.getLogger(ErrorPageServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorCode = "javax.servlet.error.status_code";
        Integer code = (Integer) request.getSession().getAttribute(errorCode);
        request.getSession().removeAttribute(errorCode);
        if (code == null) {
            code = (Integer) request.getAttribute(errorCode);
        }
        String message = code == 404 ? "PAGE NOT FOUND" : "SOMETHING GO WRONG...";

        LOG.error("Opened error page with code {} and message: {}", code, message);

        request.setAttribute("errorMessage", message);
        request.setAttribute("errorCode", code);
        request.getRequestDispatcher("WEB-INF/view/error.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
