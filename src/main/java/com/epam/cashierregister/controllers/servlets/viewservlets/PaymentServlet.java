package com.epam.cashierregister.controllers.servlets.viewservlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * Servlet for payment page view
 */
@WebServlet(name = "PaymentServlet", value = "/pay")
public class PaymentServlet extends HttpServlet {
    static Logger LOG = LogManager.getLogger(PaymentServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Opened payment servlet (/pay)");
        LOG.info("Opened payment page (pay.jsp)");
        req.getRequestDispatcher("WEB-INF/view/pay.jsp").forward(req, resp);
    }
}
