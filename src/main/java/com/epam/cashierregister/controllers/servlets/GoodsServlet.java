package com.epam.cashierregister.controllers.servlets;

import com.epam.cashierregister.model.DAO.GoodsDAO;
import com.epam.cashierregister.model.entities.goods.Goods;

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
        //todo session change on req if it is possible
        GoodsDAO goodsDAO = GoodsDAO.getInstance();
        List<Goods> goods;
        if (req.getParameter("edit") != null){
            req.setAttribute("edit", Boolean.valueOf(req.getParameter("edit")));
        } else {
            req.setAttribute("edit", false);
        }
        if (session.getAttribute("page") == null) {
            session.setAttribute("page", 0);
        }
        Integer currentPage = (Integer) session.getAttribute("page");
        goods = goodsDAO.getGoods(currentPage);
        req.setAttribute("goods", goods);
        session.setAttribute("currentPage", currentPage / 4 + 1);
        if (goods.size() < 4) {
            session.setAttribute("isLastPage", true);
        } else {
            session.setAttribute("isLastPage", false);
        }
        req.getRequestDispatcher("WEB-INF/view/goods.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int page;
        int currentPage = (Integer) session.getAttribute("page");
        page = getPage(req.getParameter("page"), currentPage, (Boolean) session.getAttribute("isLastPage"));
        if (page < 0) {
            throw new IOException();
        } else {
            session.setAttribute("page", page);
        }
        resp.sendRedirect("ChangePageServlet");
    }

    private int getPage(String page, int currentPage, boolean isLast) {
        switch (page) {
            case "first":
                return 0;
            case "right":
                if (isLast) {
                    return 0;
                }
                return currentPage + 4;
            case "left":
                if (currentPage == 0) {
                    return currentPage;
                } else {
                    return currentPage - 4;
                }
            default:
                return -1;
        }
    }
}
