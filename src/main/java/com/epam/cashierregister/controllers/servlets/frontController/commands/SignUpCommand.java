package com.epam.cashierregister.controllers.servlets.frontController.commands;

import com.epam.cashierregister.controllers.servlets.frontController.FrontCommand;
import com.epam.cashierregister.services.DAO.EmployeeDAO;
import com.epam.cashierregister.services.PasswordHashingService;
import com.epam.cashierregister.services.UploadPhotoService;
import com.epam.cashierregister.services.entities.employee.AuthorizeInfo;
import com.epam.cashierregister.services.entities.employee.Employee;
import com.epam.cashierregister.services.entities.employee.Role;
import com.epam.cashierregister.services.exeptions.DatabaseException;
import com.epam.cashierregister.services.exeptions.InvalidInputException;
import com.epam.cashierregister.services.validateServices.ValidateInputService;
import com.epam.cashierregister.services.validateServices.ValidateSignUp;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Command for sign up
 */
public class SignUpCommand extends FrontCommand {
    private EmployeeDAO employeeDAO;

    @Override
    public void initContext() throws ServletException {
        employeeDAO = (EmployeeDAO) context.getAttribute("EmployeeDAO");
    }

    @Override
    public boolean filter() throws ServletException, IOException, DatabaseException {
        ValidateInputService validateInputService = new ValidateSignUp(req, employeeDAO);
        try {
            validateInputService.validate();
        } catch (InvalidInputException e) {
            LOG.warn("Invalid input: {}", e.getMessage());
            req.getSession().setAttribute("error", e.getMessage());
            redirect("signup");
            return false;
        }
        return true;
    }

    @Override
    public void process() throws ServletException, IOException {
        LOG.info("Start sign up employee with name {}", req.getParameter("firstname"));
        Employee newEmployee;
        String photo = UploadPhotoService.uploadPhoto(req, "usersPhotos");
        if (photo == null) {
            photo = "nonuser.jpg";
        }
        newEmployee = new Employee(0, "usersPhotos/" + photo,
                req.getParameter("firstname"), req.getParameter("secondname"),
                Role.valueOf(req.getParameter("role")), new AuthorizeInfo(req.getParameter("email"), "none"));
        try {
            employeeDAO.addEmployee(newEmployee);
            LOG.info("generated id for {} - {}", newEmployee.getFirstname(), newEmployee.getId());
            String password = PasswordHashingService.hash(req.getParameter("password"), newEmployee.getId());
            employeeDAO.updatePassword(password, newEmployee);
        } catch (DatabaseException e) {
            LOG.error("Problem with sign up");
            req.getSession().setAttribute("javax.servlet.error.status_code", e.getErrorCode());
            redirect("errorPage");
        }
        redirect("employees");
    }
}
