package com.epam.cashierregister.controllers.PRG;

import com.epam.cashierregister.services.entities.check.Check;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(name = "EditGoodsInCheckServlet", value = "/editGoodsInCheck")
public class EditGoodsInCheckServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int editValue = Integer.parseInt(req.getParameter("edit_value"));
        int editedId = Integer.parseInt(req.getParameter("edit_goods_id"));
        Check active = (Check) session.getAttribute("activeCheck");
        active.getGoodsSet().forEach(goods -> {
            if(goods.getId() == editedId){
                goods.setNumbers(editValue);
                active.setTotalCost(active.getTotalCost().subtract(goods.getTotalCost()));
                goods.setTotalCost(goods.getCost().multiply(new BigDecimal(editValue)));
                active.setTotalCost(active.getTotalCost().add(goods.getTotalCost()));
            }
        });
        session.setAttribute("activeCheck", active);
        resp.sendRedirect("createCheck");
    }

}
