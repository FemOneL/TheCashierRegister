package com.epam.cashierregister.controllers.servlets.viewServlets;

import com.epam.cashierregister.services.DAO.ChecksDAO;
import com.epam.cashierregister.services.entities.check.Check;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ChecksServlet", value = "/checks")
public class ChecksServlet extends HttpServlet {
    private ChecksDAO checksDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
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
        checks = checksDAO.getChecks(currentPage);
        if (checks.size() != 0) {
            req.setAttribute("checks", checks);
            session.setAttribute("currentPage", currentPage / 21 + 1);
        } else {
            checks = checksDAO.getChecks(0);
            req.setAttribute("checks", checks);
            session.setAttribute("currentPage", 1);
            session.setAttribute("page", 0);
        }
        req.getRequestDispatcher("WEB-INF/view/checks.jsp").forward(req, resp);
    }

}
