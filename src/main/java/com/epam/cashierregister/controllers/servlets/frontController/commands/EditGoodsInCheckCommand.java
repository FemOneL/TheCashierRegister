package com.epam.cashierregister.controllers.servlets.frontController.commands;

import com.epam.cashierregister.controllers.servlets.frontController.FrontCommand;
import com.epam.cashierregister.services.entities.check.Check;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Command for edit goods in check
 */
public class EditGoodsInCheckCommand extends FrontCommand {

    @Override
    public void initContext() throws ServletException {

    }

    @Override
    public boolean filter() throws ServletException, IOException {
        return true;
    }

    @Override
    public void process() throws ServletException, IOException {
        HttpSession session = req.getSession();
        int editValue = Integer.parseInt(req.getParameter("edit_value"));
        int editedId = Integer.parseInt(req.getParameter("edit_goods_id"));
        Check active = (Check) session.getAttribute("activeCheck");
        active.getGoodsSet().forEach(goods -> {
            if (goods.getId() == editedId) {
                goods.setNumbers(editValue);
                active.setTotalCost(active.getTotalCost().subtract(goods.getTotalCost()));
                goods.setTotalCost(goods.getCost().multiply(new BigDecimal(editValue)));
                active.setTotalCost(active.getTotalCost().add(goods.getTotalCost()));
            }
        });
        session.setAttribute("activeCheck", active);
        redirect("createCheck");
    }
}
