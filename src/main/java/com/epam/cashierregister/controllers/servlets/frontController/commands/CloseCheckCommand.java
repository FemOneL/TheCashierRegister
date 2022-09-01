package com.epam.cashierregister.controllers.servlets.frontController.commands;

import com.epam.cashierregister.controllers.servlets.frontController.FrontCommand;
import com.epam.cashierregister.services.DAO.ChecksDAO;
import com.epam.cashierregister.services.entities.check.Check;
import com.epam.cashierregister.services.entities.employee.Employee;
import com.epam.cashierregister.services.entities.goods.Goods;
import com.epam.cashierregister.services.exeptions.DatabaseException;
import com.epam.cashierregister.services.exeptions.InvalidInputException;
import com.epam.cashierregister.services.ReportService;
import com.epam.cashierregister.services.validateServices.ValidateCloseCheck;
import com.epam.cashierregister.services.validateServices.ValidateInputService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Command for close check command
 */
public class CloseCheckCommand extends FrontCommand {
    private ChecksDAO checksDAO;
    private ReportService report;

    @Override
    public void initContext() throws ServletException {
        checksDAO = (ChecksDAO) context.getAttribute("ChecksDAO");
        report = (ReportService) context.getAttribute("report");
    }

    @Override
    public boolean filter() throws ServletException, IOException, DatabaseException {
        ValidateInputService validateInputService = new ValidateCloseCheck(req);
        try {
            validateInputService.validate();
        } catch (InvalidInputException e) {
            LOG.warn("Invalid input: {}", e.getMessage());
            req.getSession().setAttribute("error", e.getMessage());
            redirect("createCheck");
            return false;
        }
        return true;
    }

    @Override
    public void process() throws ServletException, IOException {
        HttpSession session = req.getSession();
        Check activeCheck = (Check) session.getAttribute("activeCheck");
        LOG.info("Try to close check where id = {}", activeCheck.getId());
        Set<Goods> goodsSet = activeCheck.getGoodsSet();
        Employee employee = (Employee) session.getAttribute("employee");
        Check newCheck = new Check(0, employee, new Timestamp(System.currentTimeMillis()), activeCheck.getTotalCost());
        newCheck.setGoodsSet(goodsSet);
        try {
            newCheck.setId(checksDAO.createCheck(newCheck));
            checksDAO.addGoodsInCheck(goodsSet, newCheck);
        } catch (DatabaseException e) {
            LOG.error("Problem with closing check");
            req.getSession().setAttribute("javax.servlet.error.status_code", e.getErrorCode());
            redirect("errorPage");
        }
        report.addSelling(1, newCheck.getTotalCost());
        session.removeAttribute("activeCheck");
        session.removeAttribute("type");
        session.removeAttribute("remainder");
        session.removeAttribute("sum");
        session.setAttribute("newCheck", newCheck);
        redirect("check");
    }
}
