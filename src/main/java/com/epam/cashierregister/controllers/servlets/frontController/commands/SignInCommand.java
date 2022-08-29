package com.epam.cashierregister.controllers.servlets.frontController.commands;

import com.epam.cashierregister.controllers.servlets.frontController.FrontCommand;
import com.epam.cashierregister.services.DAO.EmployeeDAO;
import com.epam.cashierregister.services.exeptions.InvalidInputException;
import com.epam.cashierregister.services.validateServices.ValidateInputService;
import com.epam.cashierregister.services.validateServices.ValidateSignIn;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class SignInCommand extends FrontCommand {
    private EmployeeDAO employeeDAO;

    @Override
    public void initContext() throws ServletException {
        employeeDAO = (EmployeeDAO) context.getAttribute("EmployeeDAO");
    }

    @Override
    public boolean filter() throws ServletException, IOException {
        ValidateInputService validateInputService = new ValidateSignIn(req, employeeDAO);
        try {
            validateInputService.validate();
        } catch (InvalidInputException e) {
            req.getSession().setAttribute("error", e.getMessage());
            redirect("authorize");
            return false;
        }
        return true;
    }

    @Override
    public void process() throws ServletException, IOException {
        redirect("cabinet");
    }
}
