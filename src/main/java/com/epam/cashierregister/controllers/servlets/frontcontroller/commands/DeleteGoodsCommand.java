package com.epam.cashierregister.controllers.servlets.frontcontroller.commands;

import com.epam.cashierregister.controllers.servlets.frontcontroller.FrontCommand;
import com.epam.cashierregister.services.dao.GoodsDAO;
import com.epam.cashierregister.services.exeptions.GoodsExistInCheckException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Command for delete goods
 */
public class DeleteGoodsCommand extends FrontCommand {
    private GoodsDAO goodsDAO;

    @Override
    public void initContext() throws ServletException {
        goodsDAO = (GoodsDAO) context.getAttribute("GoodsDAO");
    }

    @Override
    public boolean filter() throws ServletException, IOException {
        return true;
    }

    @Override
    public void process() throws ServletException, IOException {
        LOG.info("Delete goods where id = {}", req.getParameter("delete"));
        HttpSession session = req.getSession();
        int id = Integer.parseInt(req.getParameter("delete"));
        try {
            goodsDAO.deleteGoods(id);
        } catch (GoodsExistInCheckException e) {
            LOG.warn("{}", e.getMessage());
            session.setAttribute("error", e.getMessage());
        }
        redirect("goods?edit=true");
    }
}
