package com.epam.cashierregister.controllers.servlets.frontController.commands;

import com.epam.cashierregister.controllers.servlets.frontController.FrontCommand;
import com.epam.cashierregister.services.DAO.ChecksDAO;
import com.epam.cashierregister.services.entities.check.Check;
import com.epam.cashierregister.services.entities.goods.Goods;

import javax.servlet.ServletException;
import java.io.IOException;

public class DeleteGoodsFromCheckCommand extends FrontCommand {
    private ChecksDAO checksDAO;

    @Override
    public void initContext() throws ServletException {
        checksDAO = (ChecksDAO) context.getAttribute("ChecksDAO");
    }

    @Override
    public boolean filter() throws ServletException, IOException {
        if (req.getParameter("editNumber") == null) {
            Check activeCheck = (Check) req.getSession().getAttribute("activeCheck");
            redirect("editExisting?edit=" + activeCheck.getId());
            return false;
        }
        return true;
    }

    @Override
    public void process() throws ServletException, IOException {
        Check activeCheck = (Check) req.getSession().getAttribute("activeCheck");
        int goodsId = Integer.parseInt(req.getParameter("good"));
        int value = Integer.parseInt(req.getParameter("editNumber"));
        int different = 0;
        if (value == 0) {
            checksDAO.deleteSpecificCheck(activeCheck, goodsId);
        } else {
            for (Goods goods : activeCheck.getGoodsSet()) {
                if (goods.getId() == goodsId) {
                    if (goods.getNumbers() != value) {
                        different = goods.getNumbers() - value;
                    }
                }
            }
            if (different != 0) {
                checksDAO.deleteSpecificCheck(activeCheck, goodsId, different);
            }
        }
        redirect("editExisting?edit=" + activeCheck.getId());
    }
}
