package com.epam.cashierregister.controllers.servlets.frontController.commands;

import com.epam.cashierregister.controllers.servlets.frontController.FrontCommand;
import com.epam.cashierregister.services.DAO.EmployeeDAO;
import com.epam.cashierregister.services.PasswordHashierService;
import com.epam.cashierregister.services.UploadPhotoService;
import com.epam.cashierregister.services.entities.employee.AuthorizeInfo;
import com.epam.cashierregister.services.entities.employee.Employee;
import com.epam.cashierregister.services.entities.employee.Role;
import com.epam.cashierregister.services.exeptions.InvalidInputException;
import com.epam.cashierregister.services.validateServices.ValidateInputService;
import com.epam.cashierregister.services.validateServices.ValidateSignUp;

import javax.servlet.ServletException;
import java.io.IOException;

public class SignUpCommand extends FrontCommand {
    private EmployeeDAO employeeDAO;

    @Override
    public void initContext() throws ServletException {
        employeeDAO = (EmployeeDAO) context.getAttribute("EmployeeDAO");
    }

    @Override
    public boolean filter() throws ServletException, IOException {
        ValidateInputService validateInputService = new ValidateSignUp(req, employeeDAO);
        try {
            validateInputService.validate();
        } catch (InvalidInputException e) {
            req.getSession().setAttribute("error", e.getMessage());
            redirect("signup");
            return false;
        }
        return true;
    }

    @Override
    public void process() throws ServletException, IOException {
        Employee newEmployee;
        String photo = UploadPhotoService.uploadPhoto(req, "usersPhotos");
        if (photo == null) {
            photo = "nonuser.jpg";
        }
        newEmployee = new Employee(0, "usersPhotos/" + photo,
                req.getParameter("firstname"), req.getParameter("secondname"),
                Role.valueOf(req.getParameter("role")), new AuthorizeInfo(req.getParameter("email"), "none"));
        employeeDAO.addEmployee(newEmployee);
        LOG.info("generated id for {} - {}", newEmployee.getFirstname(), newEmployee.getId());
        String password = PasswordHashierService.hash(req.getParameter("password"), newEmployee.getId());
        employeeDAO.updatePassword(password, newEmployee);
        redirect("employees");
    }
}
