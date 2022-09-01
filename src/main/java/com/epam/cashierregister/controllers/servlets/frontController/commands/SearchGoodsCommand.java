package com.epam.cashierregister.controllers.servlets.frontController.commands;

import com.epam.cashierregister.controllers.servlets.frontController.FrontCommand;
import com.epam.cashierregister.services.DAO.GoodsDAO;
import com.epam.cashierregister.services.consts.Errors;
import com.epam.cashierregister.services.entities.check.Check;
import com.epam.cashierregister.services.entities.goods.Goods;
import com.epam.cashierregister.services.exeptions.DatabaseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Command for searching goods
 */
public class SearchGoodsCommand extends FrontCommand {
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
        LOG.info("Searching goods {} in warehouse", req.getParameter("goods"));
        HttpSession session = req.getSession();
        Check check = (Check) session.getAttribute("activeCheck");
        Goods goods = null;
        String value = req.getParameter("goods");
        try {
            goods = goodsDAO.searchGood(value, false);
        } catch (DatabaseException e) {
            LOG.error("Problem with searching goods");
            req.getSession().setAttribute("javax.servlet.error.status_code", e.getErrorCode());
            redirect("errorPage");
        }
        if (goods != null) {
            goods.setTotalNumber(goods.getNumbers());
            goods.setTotalCost(goods.getCost());
            goods.setNumbers(1);
            if (check.getGoodsSet().add(goods)) {
                check.setTotalCost(check.getTotalCost().add(goods.getTotalCost()));
            } else {
                int num = check.getGoodsSet().stream()
                        .filter(g -> String.valueOf(g.getId()).equals(value) || g.getModel().equals(value))
                        .mapToInt(Goods::getNumbers)
                        .sum();
                if ((num + 1) <= goods.getTotalNumber()) {
                    check.getGoodsSet().stream()
                            .filter(g -> String.valueOf(g.getId()).equals(value) || g.getModel().equals(value))
                            .forEach(g -> {
                                g.setNumbers(g.getNumbers() + 1);
                                g.setTotalCost(g.getCost().multiply(new BigDecimal(g.getNumbers())));
                            });
                    check.setTotalCost(check.getTotalCost().add(goods.getTotalCost()));
                }
            }
            session.setAttribute("activeCheck", check);
        } else {
            LOG.info("Goods under this name/id is not in warehouse");
            session.setAttribute("error", Errors.GOODS_NOT_IN_WAREHOUSE.name());
        }
        redirect("createCheck");
    }
}
