package com.epam.cashierregister.controllers.filters.validateAuthorizeFilters;

import com.epam.cashierregister.services.DAO.EmployeeDAO;
import com.epam.cashierregister.services.entities.employee.Employee;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "ValidateData")
public class ValidateDataFilter implements Filter {
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
                myEmployee.setAuthorize(null);
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
