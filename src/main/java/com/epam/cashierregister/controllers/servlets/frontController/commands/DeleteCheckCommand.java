package com.epam.cashierregister.controllers.servlets.frontController.commands;

import com.epam.cashierregister.controllers.servlets.frontController.FrontCommand;
import com.epam.cashierregister.services.DAO.ChecksDAO;
import com.epam.cashierregister.services.entities.check.Check;
import com.epam.cashierregister.services.ReportService;
import com.epam.cashierregister.services.entities.goods.Goods;
import com.epam.cashierregister.services.exeptions.DatabaseException;

import javax.servlet.*;
import java.io.IOException;

public class DeleteCheckCommand extends FrontCommand {
    private ChecksDAO checksDAO;
    private ReportService report;

    @Override
    public void initContext() throws ServletException {
        checksDAO = (ChecksDAO) context.getAttribute("ChecksDAO");
        report = (ReportService) context.getAttribute("report");
    }

    @Override
    public boolean filter() throws ServletException, IOException {return true;}

    @Override
    public void process() throws ServletException, IOException {
        Check check = (Check) req.getSession().getAttribute("activeCheck");
        LOG.info("Try to delete check where id = {}", check.getId());
        try {
            checksDAO.deleteCheck(check);
        } catch (DatabaseException e) {
            LOG.error("Problem with deleting check");
            req.getSession().setAttribute("javax.servlet.error.status_code", e.getErrorCode());
            redirect("errorPage");
        }
        int goodsNumber = check.getGoodsSet().stream().mapToInt(Goods::getNumbers).reduce(0, Integer::sum);
        report.addReturned(goodsNumber, check.getTotalCost());
        redirect("checks");
    }
}
