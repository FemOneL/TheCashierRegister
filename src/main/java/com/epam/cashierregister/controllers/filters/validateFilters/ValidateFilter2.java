package com.epam.cashierregister.controllers.filters.validateFilters;

import com.epam.cashierregister.model.DAO.EmployeeDAO;
import com.epam.cashierregister.model.entities.employee.Employee;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "ValidateFilter2", value = "/cabinet")
public class ValidateFilter2 implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        EmployeeDAO employeeDAO = EmployeeDAO.getInstance();
        Employee myEmployee;
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        if (session.getAttribute("employee") == null) {
            myEmployee = employeeDAO.getEmployee((String) session.getAttribute("tempLogin"), (String) session.getAttribute("tempPassword"));
            if (myEmployee != null) {
                session.setAttribute("employee", myEmployee);
                chain.doFilter(request, response);
            } else {
                session.setAttribute("error", "employee not found");
                resp.sendRedirect("/cashier/");
            }
        }else {
            chain.doFilter(request, response);
        }
    }
}
