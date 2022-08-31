package com.epam.cashierregister.controllers.servlets.viewServlets;

import com.epam.cashierregister.services.DAO.GoodsDAO;
import com.epam.cashierregister.services.entities.goods.Goods;
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
import java.util.List;

@WebServlet(name = "GoodsServlet", value = "/goods")
public class GoodsServlet extends HttpServlet {
    private GoodsDAO goodsDAO;
    static Logger LOG = LogManager.getLogger(GoodsServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        LOG.info("Opened goods servlet (/goods)");
        goodsDAO = (GoodsDAO) config.getServletContext().getAttribute("GoodsDAO");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        List<Goods> goods;

        // check edit mode
        if (req.getParameter("edit") != null){
            req.setAttribute("edit", Boolean.valueOf(req.getParameter("edit")));
        } else {
            req.setAttribute("edit", false);
        }
        // page
        if (session.getAttribute("page") == null) {
            session.setAttribute("page", 0);
        }

        Integer currentPage = (Integer) session.getAttribute("page");
        try {
            goods = goodsDAO.getGoods(currentPage, (String) session.getAttribute("search"));
            if (goods.size() != 0) {
                req.setAttribute("goods", goods);
                session.setAttribute("currentPage", currentPage / 4 + 1);
            } else {
                goods = goodsDAO.getGoods(0, (String) session.getAttribute("search"));
                req.setAttribute("goods", goods);
                session.setAttribute("currentPage", 1);
                session.setAttribute("page", 0);
            }
        } catch (DatabaseException e) {
            LOG.error("Problem with goods page");
            req.getSession().setAttribute("javax.servlet.error.status_code", e.getErrorCode());
            resp.sendRedirect("errorPage");
        }
        LOG.info("Opened goods page (goods.jsp)");
        req.getRequestDispatcher("WEB-INF/view/goods.jsp").forward(req, resp);
    }
}
