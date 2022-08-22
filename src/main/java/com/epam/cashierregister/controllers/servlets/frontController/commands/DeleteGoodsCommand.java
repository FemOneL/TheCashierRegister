package com.epam.cashierregister.controllers.servlets.frontController.commands;

import com.epam.cashierregister.controllers.servlets.frontController.FrontCommand;
import com.epam.cashierregister.services.DAO.GoodsDAO;
import com.epam.cashierregister.services.exeptions.GoodsExistInCheckException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class DeleteGoodsCommand extends FrontCommand {
    private GoodsDAO goodsDAO;

    @Override
    public void initContext() throws ServletException {
        goodsDAO = (GoodsDAO) context.getAttribute("GoodsDAO");
    }

    @Override
    public boolean filter() throws ServletException, IOException {return true;}

    @Override
    public void process() throws ServletException, IOException {
        HttpSession session = req.getSession();
        int id = Integer.parseInt(req.getParameter("delete"));
        try {
            goodsDAO.deleteGoods(id);
            session.setAttribute("error", " ");
        } catch (GoodsExistInCheckException e) {
            session.setAttribute("error", e.getMessage());
        }
        redirect("goods?edit=true");
    }
}
