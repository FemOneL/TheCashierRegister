package com.epam.cashierregister.controllers.servlets.viewservlets;

import com.epam.cashierregister.services.dao.ReportDAO;
import com.epam.cashierregister.services.entities.report.Report;
import com.epam.cashierregister.services.exeptions.DatabaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

/**
 * Servlet for reports page view
 */
@WebServlet(name = "ReportsServlet", value = "/reports")
public class ReportsServlet extends HttpServlet {
    private ReportDAO reportDAO;
    static Logger LOG = LogManager.getLogger(ReportsServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        LOG.info("Opened reports servlet (/reports)");
        reportDAO = (ReportDAO) config.getServletContext().getAttribute("ReportDAO");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Set<Report> reports;
        if (session.getAttribute("page") == null) {
            session.setAttribute("page", 0);
        }
        Integer currentPage = (Integer) session.getAttribute("page");
        try {
            reports = reportDAO.getReports(currentPage, (String) session.getAttribute("search"));
            if (!reports.isEmpty()) {
                req.setAttribute("reports", reports);
                session.setAttribute("currentPage", currentPage / 5 + 1);
            } else {
                reports = reportDAO.getReports(0, (String) session.getAttribute("search"));
                req.setAttribute("reports", reports);
                session.setAttribute("currentPage", 1);
                session.setAttribute("page", 0);
            }
        } catch (DatabaseException e) {
            LOG.error("Problem with reports page");
            req.getSession().setAttribute("javax.servlet.error.status_code", e.getErrorCode());
            resp.sendRedirect("errorPage");
        }
        LOG.info("Opened reports page (reports.jsp)");
        req.getRequestDispatcher("WEB-INF/view/reports.jsp").forward(req, resp);
    }
}
