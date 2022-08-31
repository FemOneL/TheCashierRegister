package com.epam.cashierregister.controllers.servlets.viewServlets;

import com.epam.cashierregister.services.DAO.ChecksDAO;
import com.epam.cashierregister.services.DAO.ReportDAO;
import com.epam.cashierregister.services.entities.check.Check;
import com.epam.cashierregister.services.entities.report.Report;
import com.epam.cashierregister.services.exeptions.DatabaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Set;

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
            if (reports.size() != 0) {
                req.setAttribute("reports", reports);
                session.setAttribute("currentPage", currentPage / 5 + 1);
            } else {
                reports = reportDAO.getReports(0, (String) session.getAttribute("search"));
                req.setAttribute("reports", reports);
                session.setAttribute("currentPage", 1);
                session.setAttribute("page", 0);
            }
        }catch (DatabaseException e){
            LOG.error("Problem with reports page");
            req.getSession().setAttribute("javax.servlet.error.status_code", e.getErrorCode());
            resp.sendRedirect("errorPage");
        }
        LOG.info("Opened reports page (reports.jsp)");
        req.getRequestDispatcher("WEB-INF/view/reports.jsp").forward(req, resp);
    }
}
