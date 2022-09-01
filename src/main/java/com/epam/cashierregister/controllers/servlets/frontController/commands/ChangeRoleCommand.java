package com.epam.cashierregister.controllers.servlets.frontController.commands;

import com.epam.cashierregister.controllers.servlets.frontController.FrontCommand;
import com.epam.cashierregister.services.DAO.EmployeeDAO;
import com.epam.cashierregister.services.entities.employee.Role;
import com.epam.cashierregister.services.exeptions.DatabaseException;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Command for change role
 */
public class ChangeRoleCommand extends FrontCommand {
    private EmployeeDAO employeeDAO;

    @Override
    public void initContext() throws ServletException {
        employeeDAO = (EmployeeDAO) context.getAttribute("EmployeeDAO");
    }

    @Override
    public boolean filter() throws ServletException, IOException {
        return true;
    }

    @Override
    public void process() throws ServletException, IOException {
        LOG.info("Try to change role for employee with id {}", req.getParameter("emdId"));
        try {
            employeeDAO.changeRole(Role.valueOf(req.getParameter("role")), Integer.parseInt(req.getParameter("empId")));
        } catch (DatabaseException e) {
            LOG.error("Problem with changing role");
            req.getSession().setAttribute("javax.servlet.error.status_code", e.getErrorCode());
            redirect("errorPage");
        }
        redirect("employees");
    }
}
