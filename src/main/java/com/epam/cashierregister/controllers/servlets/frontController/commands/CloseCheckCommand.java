package com.epam.cashierregister.controllers.servlets.frontController.commands;

import com.epam.cashierregister.controllers.servlets.frontController.FrontCommand;
import com.epam.cashierregister.services.DAO.ChecksDAO;
import com.epam.cashierregister.services.entities.check.Check;
import com.epam.cashierregister.services.entities.employee.Employee;
import com.epam.cashierregister.services.entities.goods.Goods;
import com.epam.cashierregister.services.exeptions.InvalidInputException;
import com.epam.cashierregister.services.ReportService;
import com.epam.cashierregister.services.validateServices.ValidateCloseCheck;
import com.epam.cashierregister.services.validateServices.ValidateInputService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Set;

public class CloseCheckCommand extends FrontCommand {
    private ChecksDAO checksDAO;
    private ReportService report;

    @Override
    public void initContext() throws ServletException {
        checksDAO = (ChecksDAO) context.getAttribute("ChecksDAO");
        report = (ReportService) context.getAttribute("report");
    }

    @Override
    public boolean filter() throws ServletException, IOException {
        ValidateInputService validateInputService = new ValidateCloseCheck(req);
        try {
            validateInputService.validate();
        } catch (InvalidInputException e) {
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
        Set<Goods> goodsSet = activeCheck.getGoodsSet();
        Employee employee = (Employee) session.getAttribute("employee");
        Check newCheck = new Check(0, employee, new Timestamp(System.currentTimeMillis()), activeCheck.getTotalCost());
        newCheck.setGoodsSet(goodsSet);
        newCheck.setId(checksDAO.createCheck(newCheck));
        checksDAO.addGoodsInCheck(goodsSet, newCheck);
        report.addSelling(1, newCheck.getTotalCost());
        session.removeAttribute("activeCheck");
        session.removeAttribute("type");
        session.removeAttribute("remainder");
        session.removeAttribute("sum");
        session.removeAttribute("error");
        session.setAttribute("newCheck", newCheck);
        redirect("check");
    }
}
