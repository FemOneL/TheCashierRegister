package com.epam.cashierregister.controllers.PRG;

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

@WebServlet(name = "AddNewGoodsServlet", value = "/AddNewGoods")
public class AddNewGoodsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        //todo urf8
        CategoriesDAO categoriesDAO = CategoriesDAO.getInstance();
        ProducersDAO producersDAO = ProducersDAO.getInstance();
        GoodsDAO goodsDAO = GoodsDAO.getInstance();
        boolean isNewCategory = false;
        boolean isNewProducer = false;
        String model = req.getParameter("model");
        int numbers = Integer.parseInt(req.getParameter("number"));
        BigDecimal cost = BigDecimal.valueOf(Double.parseDouble(req.getParameter("cost") + "." + req.getParameter("cents")));
        Category category;
        Producer producer;
        String selectCategory = req.getParameter("select_category");
        if (!selectCategory.equals("none")){
            category = new Category(selectCategory);
        }else {
            category = new Category(req.getParameter("new_category"));
            isNewCategory = true;
        }
        String selectProducer = req.getParameter("select_producer");
        if (!selectProducer.equals("none")){
            producer = new Producer(selectProducer);
        }else {
            producer = new Producer(req.getParameter("new_producer"));
            isNewProducer = true;
        }
        Goods myGoods = new Goods(0, model, numbers, cost, category, producer);
        if (isNewCategory){
            categoriesDAO.addNewCategory(category);
        }
        if (isNewProducer){
            producersDAO.addNewProducer(producer);
        }
        goodsDAO.addGoods(myGoods);
        resp.sendRedirect("addGoods");
    }
}
