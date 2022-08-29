package com.epam.cashierregister.controllers.servlets.frontController.commands;

import com.epam.cashierregister.controllers.servlets.frontController.FrontCommand;
import com.epam.cashierregister.services.entities.check.Check;
import com.epam.cashierregister.services.exeptions.InvalidInputException;
import com.epam.cashierregister.services.validateServices.ValidateInputService;
import com.epam.cashierregister.services.validateServices.ValidateRemainder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

public class ReminderCommand extends FrontCommand {

    @Override
    public void initContext() throws ServletException {

    }

    @Override
    public boolean filter() throws ServletException, IOException {
        ValidateInputService validateInputService = new ValidateRemainder(req);
        try {
            validateInputService.validate();
            req.getSession().removeAttribute("remainder");
        } catch (InvalidInputException e) {
            req.getSession().setAttribute("error", e.getMessage());
            redirect("pay");
            return false;
        }
        return true;
    }

    @Override
    public void process() throws ServletException, IOException {
        String input = req.getParameter("count");
        BigDecimal sum = new BigDecimal(input);
        HttpSession session = req.getSession();
        Check check = (Check) session.getAttribute("activeCheck");
        session.setAttribute("sum", sum);
        if (sum.doubleValue() < check.getTotalCost().doubleValue()) {
            session.setAttribute("error", "Pay another " + (check.getTotalCost().doubleValue() - sum.doubleValue()));
            redirect("pay");
        } else if (sum.doubleValue() >= check.getTotalCost().doubleValue()) {
            session.setAttribute("remainder", sum.subtract(check.getTotalCost()));
            redirect("pay");
        }
    }
}
