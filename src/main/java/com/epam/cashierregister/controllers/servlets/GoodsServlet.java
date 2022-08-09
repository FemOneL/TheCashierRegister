package com.epam.cashierregister.controllers.servlets;

import com.epam.cashierregister.services.DAO.GoodsDAO;
import com.epam.cashierregister.services.entities.goods.Goods;

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
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        GoodsDAO goodsDAO = GoodsDAO.getInstance();
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
        goods = goodsDAO.getGoods(currentPage);
        if (goods.size() != 0) {
            req.setAttribute("goods", goods);
            session.setAttribute("currentPage", currentPage / 4 + 1);
        } else {
            goods = goodsDAO.getGoods(0);
            req.setAttribute("goods", goods);
            session.setAttribute("currentPage", 1);
            session.setAttribute("page", 0);
        }

        req.getRequestDispatcher("WEB-INF/view/goods.jsp").forward(req, resp);
    }
}
