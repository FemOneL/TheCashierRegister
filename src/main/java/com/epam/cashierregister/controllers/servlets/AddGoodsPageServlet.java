package com.epam.cashierregister.controllers.servlets;

import com.epam.cashierregister.services.DAO.CategoriesDAO;
import com.epam.cashierregister.services.DAO.GoodsDAO;
import com.epam.cashierregister.services.DAO.ProducersDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AddGoodsPageServlet", value = "/addGoods")
public class AddGoodsPageServlet extends HttpServlet {
    private CategoriesDAO categoriesDAO;
    private ProducersDAO producersDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        categoriesDAO = (CategoriesDAO) config.getServletContext().getAttribute("CategoriesDAO");
        producersDAO = (ProducersDAO) config.getServletContext().getAttribute("ProducersDAO");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        req.setAttribute("categories", categoriesDAO.getCategoryList());
        req.setAttribute("producers", producersDAO.getProducerList());
        if (session.getAttribute("error") == null){
            session.setAttribute("error", " ");
        }
        req.getRequestDispatcher("WEB-INF/view/addGoods.jsp").forward(req, resp);
    }
}
