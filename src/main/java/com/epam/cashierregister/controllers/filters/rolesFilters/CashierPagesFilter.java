package com.epam.cashierregister.controllers.filters.rolesFilters;

import com.epam.cashierregister.services.entities.employee.Employee;
import com.epam.cashierregister.services.entities.employee.Role;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "CashierPagesFilter", value = { "/createCheck" })
public class CashierPagesFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        if (session.getAttribute("employee") != null){
            Employee emp = (Employee) session.getAttribute("employee");
            if (emp.getRole().name().equals(Role.CASHIER.name())) {
                chain.doFilter(request, response);
            } else {
                resp.sendRedirect("cabinet");
            }
        }else{
            session.setAttribute("error", "you must authorize!");
            resp.sendRedirect("authorize");
        }
    }
}
