package com.epam.cashierregister.controllers.filters;

import com.epam.cashierregister.model.entities.employee.Employee;
import com.epam.cashierregister.model.entities.employee.Role;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AuthorizeCheckFilter", value = "/goods")
public class GoodsCheckFilter implements Filter {
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
            if (emp.getRole().name().equals(Role.COMMODITY_EXPERT.name())) {
                chain.doFilter(request, response);
            } else {
                resp.sendRedirect("/cashier/cabinet");
            }
        }else{
            session.setAttribute("error", "you must authorize!");
            resp.sendRedirect("/cashier/");
        }
    }
}
