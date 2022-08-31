package com.epam.cashierregister.controllers.servlets.viewServlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ReportViewServlet", value = "/report")
public class ReportViewServlet extends HttpServlet {
    static Logger LOG = LogManager.getLogger(ReportViewServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Opened report view servlet (/report)");
        LOG.info("Opened report view page (report.jsp)");
        req.getRequestDispatcher("WEB-INF/view/report.jsp").forward(req, resp);
    }

}
