package com.epam.cashierregister.controllers.servlets.frontcontroller.commands;

import com.epam.cashierregister.controllers.servlets.frontcontroller.FrontCommand;
import com.epam.cashierregister.services.dao.EmployeeDAO;
import com.epam.cashierregister.services.exeptions.DatabaseException;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Command for delete employee
 */
public class DeleteEmployeeCommand extends FrontCommand {
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
        try {
            employeeDAO.deleteEmployee(employeeDAO.getEmployee(Integer.parseInt(req.getParameter("empId"))));
        } catch (DatabaseException e) {
            LOG.error("Problem with delete employee");
            req.getSession().setAttribute("javax.servlet.error.status_code", e.getErrorCode());
            redirect("errorPage");
            return;
        }
        redirect("employees");
    }
}
