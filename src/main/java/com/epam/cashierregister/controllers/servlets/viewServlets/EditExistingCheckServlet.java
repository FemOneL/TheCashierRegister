package com.epam.cashierregister.controllers.servlets.viewServlets;

import com.epam.cashierregister.services.DAO.ChecksDAO;
import com.epam.cashierregister.services.entities.check.Check;
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

/**
 * Servlet for edit existing check page view
 */
@WebServlet(name = "EditExistingCheckServlet", value = "/editExisting")
public class EditExistingCheckServlet extends HttpServlet {
    private ChecksDAO checksDAO;
    static Logger LOG = LogManager.getLogger(EditExistingCheckServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        LOG.info("Opened edit existing check servlet servlet (/editExisting)");
        checksDAO = (ChecksDAO) config.getServletContext().getAttribute("ChecksDAO");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Check check = null;
        try {
            check = checksDAO.getCheckWithGoods(Integer.parseInt(req.getParameter("edit")));
            if (session.getAttribute("error") == null) {
                session.setAttribute("error", " ");
            }
            if (check.getGoodsSet().size() == 0) {
                checksDAO.deleteCheck(check);
                resp.sendRedirect("checks");
            } else {
                session.setAttribute("activeCheck", check);
                LOG.info("Opened edit check page (editCheck.jsp)");
                req.getRequestDispatcher("WEB-INF/view/editCheck.jsp").forward(req, resp);
            }
        } catch (DatabaseException e) {
            LOG.error("Problem with editing existing check");
            req.getSession().setAttribute("javax.servlet.error.status_code", e.getErrorCode());
            resp.sendRedirect("errorPage");
        }
    }
}
