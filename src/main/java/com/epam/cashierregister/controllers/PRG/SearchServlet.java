package com.epam.cashierregister.controllers.PRG;

import com.epam.cashierregister.services.DAO.GoodsDAO;
import com.epam.cashierregister.services.entities.check.Check;
import com.epam.cashierregister.services.entities.goods.Goods;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(name = "SearchServlet", value = "/search")
public class SearchServlet extends HttpServlet {
    private GoodsDAO goodsDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        goodsDAO = (GoodsDAO) config.getServletContext().getAttribute("GoodsDAO");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Check check = (Check) session.getAttribute("activeCheck");
        Goods goods;
        String value = req.getParameter("goods");
        goods = goodsDAO.searchGood(value);
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
        resp.sendRedirect("createCheck");
    }


}
