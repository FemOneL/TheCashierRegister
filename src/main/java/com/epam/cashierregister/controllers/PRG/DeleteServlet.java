package com.epam.cashierregister.controllers.PRG;

import com.epam.cashierregister.services.DAO.GoodsDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "DeleteServlet", value = "/delete")
public class DeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GoodsDAO goodsDAO = GoodsDAO.getInstance();
        int id = Integer.parseInt(req.getParameter("delete"));
        goodsDAO.deleteGoods(id);
        resp.sendRedirect("goods?edit=true");
    }

}
