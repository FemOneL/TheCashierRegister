package com.epam.cashierregister.controllers.servlets.frontcontroller.commands;

import com.epam.cashierregister.controllers.servlets.frontcontroller.FrontCommand;
import com.epam.cashierregister.services.dao.EmployeeDAO;
import com.epam.cashierregister.services.EmailSenderService;
import com.epam.cashierregister.services.PasswordHashingService;
import com.epam.cashierregister.services.ValidateService;
import com.epam.cashierregister.services.consts.Errors;
import com.epam.cashierregister.services.entities.employee.Employee;
import com.epam.cashierregister.services.exeptions.DatabaseException;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Command for changing password
 */
public class ChangePasswordCommand extends FrontCommand {
    private EmployeeDAO employeeDAO;
    private ValidateService validateService;

    @Override
    public void initContext() throws ServletException {
        employeeDAO = (EmployeeDAO) context.getAttribute("EmployeeDAO");
        validateService = (ValidateService) context.getAttribute("validateService");
    }

    @Override
    public boolean filter() throws ServletException, IOException, DatabaseException {
        if (!validateService.validatePassword(req.getParameter("newPassword"))){
            req.getSession().setAttribute("error", Errors.WRONG_INPUT.name());
            redirect("employees");
            return false;
        }else {
            return true;
        }
    }

    @Override
    public void process() throws ServletException, IOException {
        EmailSenderService emailSenderService = new EmailSenderService();
        String gmail = req.getParameter("gmail");
        String unhashingPassword = req.getParameter("newPassword");
        try {
            Employee employee = employeeDAO.getEmployee(gmail);
            employee.getAuthorize().setId(employeeDAO.getAuthorizeID(gmail));
            String password = PasswordHashingService.hash(unhashingPassword, employee.getId());
            employeeDAO.updatePassword(password, employee);
            emailSenderService.sendPassword(gmail, unhashingPassword);
        } catch (DatabaseException e) {
            LOG.error("Problem with updating password");
            req.getSession().setAttribute("javax.servlet.error.status_code", e.getErrorCode());
            redirect("errorPage");
        } catch (MessagingException e){
            LOG.error("Failed sending email: ", e);
        }
        redirect("employees");
    }
}
