package com.epam.cashierregister.controllers.servlets.frontController.commands;

import com.epam.cashierregister.controllers.servlets.frontController.FrontCommand;
import com.epam.cashierregister.services.DAO.EmployeeDAO;
import com.epam.cashierregister.services.entities.employee.Role;

import javax.servlet.ServletException;
import java.io.IOException;

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
        employeeDAO.changeRole(Role.valueOf(req.getParameter("role")), Integer.parseInt(req.getParameter("empId")));
        redirect("employees");
    }
}
