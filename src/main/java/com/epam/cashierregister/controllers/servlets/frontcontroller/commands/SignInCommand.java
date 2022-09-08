package com.epam.cashierregister.controllers.servlets.frontcontroller.commands;

import com.epam.cashierregister.controllers.servlets.frontcontroller.FrontCommand;
import com.epam.cashierregister.services.dao.EmployeeDAO;
import com.epam.cashierregister.services.exeptions.DatabaseException;
import com.epam.cashierregister.services.exeptions.InvalidInputException;
import com.epam.cashierregister.services.validateservices.ValidateInputService;
import com.epam.cashierregister.services.validateservices.ValidateSignIn;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Command for sign in
 */
public class SignInCommand extends FrontCommand {
    private EmployeeDAO employeeDAO;

    @Override
    public void initContext() throws ServletException {
        employeeDAO = (EmployeeDAO) context.getAttribute("EmployeeDAO");
    }

    @Override
    public boolean filter() throws ServletException, IOException, DatabaseException {
        ValidateInputService validateInputService = new ValidateSignIn(req, employeeDAO);
        try {
            validateInputService.validate();
        } catch (InvalidInputException e) {
            LOG.warn("Invalid input: {}", e.getMessage());
            req.getSession().setAttribute("error", e.getMessage());
            redirect("authorize");
            return false;
        }
        return true;
    }

    @Override
    public void process() throws ServletException, IOException {
        LOG.info("Success sign in");
        redirect("cabinet");
    }
}
