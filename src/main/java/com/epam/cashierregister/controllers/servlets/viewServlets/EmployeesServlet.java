package com.epam.cashierregister.controllers.servlets.viewServlets;

import com.epam.cashierregister.services.DAO.ChecksDAO;
import com.epam.cashierregister.services.DAO.EmployeeDAO;
import com.epam.cashierregister.services.entities.check.Check;
import com.epam.cashierregister.services.entities.employee.Employee;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@WebServlet(name = "EmployeesServlet", value = "/employees")
public class EmployeesServlet extends HttpServlet {
    private EmployeeDAO employeeDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        employeeDAO = (EmployeeDAO) config.getServletContext().getAttribute("EmployeeDAO");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Set<Employee> employees;
        if (session.getAttribute("page") == null) {
            session.setAttribute("page", 0);
        }
        Integer currentPage = (Integer) session.getAttribute("page");
        employees = employeeDAO.getEmployees(currentPage);
        if (employees.size() != 0) {
            req.setAttribute("employees", employees);
            session.setAttribute("currentPage", currentPage + 1);
        } else {
            employees = employeeDAO.getEmployees(0);
            req.setAttribute("employees", employees);
            session.setAttribute("currentPage", 1);
            session.setAttribute("page", 0);
        }
        req.setAttribute("roles", employeeDAO.getRoles());
        req.getRequestDispatcher("WEB-INF/view/employees.jsp").forward(req, resp);
    }
}
