package com.epam.cashierregister.controllers.servlets.frontController.commands;

import com.epam.cashierregister.controllers.servlets.frontController.FrontCommand;
import com.epam.cashierregister.services.DAO.GoodsDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import java.io.IOException;

public class EditGoodsInWarehouseCommand extends FrontCommand {
    static Logger LOG = LogManager.getLogger(EditGoodsInWarehouseCommand.class);
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
        LOG.info("Change number for id = {} to {}", req.getParameter("id"), req.getParameter("numb"));
        int id = Integer.parseInt(req.getParameter("id"));
        int newNumber = Integer.parseInt(req.getParameter("numb"));
        if (goodsDAO.updateNumber(id, newNumber)) {
            LOG.info("Success update");
        } else {
            LOG.warn("Failed update");
        }
        redirect("goods?edit=true");
    }
}
