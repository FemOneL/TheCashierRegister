package com.epam.cashierregister.controllers.servlets.frontController.commands;

import com.epam.cashierregister.controllers.servlets.frontController.FrontCommand;
import com.epam.cashierregister.services.DAO.GoodsDAO;
import com.epam.cashierregister.services.entities.check.Check;
import com.epam.cashierregister.services.entities.goods.Goods;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

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
        HttpSession session = req.getSession();
        Check check = (Check) session.getAttribute("activeCheck");
        Goods goods;
        String value = req.getParameter("goods");
        goods = goodsDAO.searchGood(value, false);
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
            session.setAttribute("error", " ");
        } else {
            session.setAttribute("error", "Goods under this name/id is not in warehouse");
        }
        redirect("createCheck");
    }
}
