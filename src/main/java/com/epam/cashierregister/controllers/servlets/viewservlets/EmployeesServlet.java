package com.epam.cashierregister.controllers.servlets.viewservlets;

import com.epam.cashierregister.services.dao.EmployeeDAO;
import com.epam.cashierregister.services.entities.employee.Employee;
import com.epam.cashierregister.services.exeptions.DatabaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

/**
 * Servlet for employees page view
 */
@WebServlet(name = "EmployeesServlet", value = "/employees")
public class EmployeesServlet extends HttpServlet {
    private EmployeeDAO employeeDAO;
    static Logger LOG = LogManager.getLogger(EmployeesServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        LOG.info("Opened employees servlet (/employees)");
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
        try {
            employees = employeeDAO.getEmployees(currentPage, (String) session.getAttribute("search"));
            if (!employees.isEmpty()) {
                req.setAttribute("employees", employees);
                session.setAttribute("currentPage", currentPage + 1);
            } else {
                employees = employeeDAO.getEmployees(0, (String) session.getAttribute("search"));
                req.setAttribute("employees", employees);
                session.setAttribute("currentPage", 1);
                session.setAttribute("page", 0);
            }
            req.setAttribute("roles", employeeDAO.getRoles());
        } catch (DatabaseException e) {
            LOG.error("Problem with employees page");
            req.getSession().setAttribute("javax.servlet.error.status_code", e.getErrorCode());
            resp.sendRedirect("errorPage");
            return;
        }
        LOG.info("Opened employees page (employees.jsp)");
        req.getRequestDispatcher("WEB-INF/view/employees.jsp").forward(req, resp);
    }
}
