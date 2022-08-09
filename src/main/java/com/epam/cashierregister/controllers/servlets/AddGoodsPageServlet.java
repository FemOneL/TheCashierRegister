package com.epam.cashierregister.controllers.servlets;

import com.epam.cashierregister.services.DAO.CategoriesDAO;
import com.epam.cashierregister.services.DAO.GoodsDAO;
import com.epam.cashierregister.services.DAO.ProducersDAO;
import com.epam.cashierregister.services.entities.goods.Category;
import com.epam.cashierregister.services.entities.goods.Goods;
import com.epam.cashierregister.services.entities.goods.Producer;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(name = "AddGoodsPageServlet", value = "/addGoods")
public class AddGoodsPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CategoriesDAO categoriesDAO = CategoriesDAO.getInstance();
        ProducersDAO producersDAO = ProducersDAO.getInstance();
        HttpSession session = req.getSession();
        req.setAttribute("categories", categoriesDAO.getCategoryList());
        req.setAttribute("producers", producersDAO.getProducerList());
        if (session.getAttribute("error") == null){
            session.setAttribute("error", " ");
        }
        req.getRequestDispatcher("WEB-INF/view/addGoods.jsp").forward(req, resp);
    }
}
