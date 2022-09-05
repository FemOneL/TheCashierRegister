package com.epam.cashierregister.controllers.servlets.viewServlets;

import com.epam.cashierregister.services.DAO.ChecksDAO;
import com.epam.cashierregister.services.entities.check.Check;
import com.epam.cashierregister.services.exeptions.DatabaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

/**
 * Servlet for checks page view
 */
@WebServlet(name = "ChecksServlet", value = "/checks")
public class ChecksServlet extends HttpServlet {
    static Logger LOG = LogManager.getLogger(ChecksServlet.class);
    private ChecksDAO checksDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        LOG.info("Opened checks servlet (/checks)");
        checksDAO = (ChecksDAO) config.getServletContext().getAttribute("ChecksDAO");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        List<Check> checks;
        if (session.getAttribute("page") == null) {
            session.setAttribute("page", 0);
        }
        Integer currentPage = (Integer) session.getAttribute("page");
        try {
            checks = checksDAO.getChecks(currentPage, (String) session.getAttribute("search"));
            if (checks.size() != 0) {
                req.setAttribute("checks", checks);
                session.setAttribute("currentPage", currentPage / 21 + 1);
            } else {
                checks = checksDAO.getChecks(0, (String) session.getAttribute("search"));
                req.setAttribute("checks", checks);
                session.setAttribute("currentPage", 1);
                session.setAttribute("page", 0);
            }
        } catch (DatabaseException e) {
            LOG.error("Problem with checks page");
            req.getSession().setAttribute("javax.servlet.error.status_code", e.getErrorCode());
            resp.sendRedirect("errorPage");
            return;
        }
        LOG.info("Opened checks page (checks.jsp)");
        req.getRequestDispatcher("WEB-INF/view/checks.jsp").forward(req, resp);
    }

}
