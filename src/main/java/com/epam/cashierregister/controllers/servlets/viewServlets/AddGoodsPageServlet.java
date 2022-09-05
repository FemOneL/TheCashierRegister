package com.epam.cashierregister.controllers.servlets.viewServlets;

import com.epam.cashierregister.services.DAO.CategoriesDAO;
import com.epam.cashierregister.services.DAO.ProducersDAO;
import com.epam.cashierregister.services.exeptions.DatabaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for add goods page view
 */
@WebServlet(name = "AddGoodsPageServlet", value = "/addGoods")
public class AddGoodsPageServlet extends HttpServlet {
    static Logger LOG = LogManager.getLogger(AddGoodsPageServlet.class);
    private CategoriesDAO categoriesDAO;
    private ProducersDAO producersDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        LOG.info("Opened add goods servlet (/addGoods)");
        categoriesDAO = (CategoriesDAO) config.getServletContext().getAttribute("CategoriesDAO");
        producersDAO = (ProducersDAO) config.getServletContext().getAttribute("ProducersDAO");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("categories", categoriesDAO.getCategoryList());
            req.setAttribute("producers", producersDAO.getProducerList());
        } catch (DatabaseException e) {
            LOG.error("Problem with adding goods");
            req.getSession().setAttribute("javax.servlet.error.status_code", e.getErrorCode());
            resp.sendRedirect("errorPage");
            return;
        }
        LOG.info("Opened add goods page (addGoods.jsp)");
        req.getRequestDispatcher("WEB-INF/view/addGoods.jsp").forward(req, resp);
    }
}
