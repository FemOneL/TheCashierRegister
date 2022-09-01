package com.epam.cashierregister.controllers.filters.rolesFilters;

import com.epam.cashierregister.services.consts.Errors;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter for cabinet page
 */
@WebFilter(filterName = "CabinetFilter", value = "/cabinet")
public class CabinetFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        if (session.getAttribute("employee") == null){
            session.setAttribute("error", Errors.YOU_MUST_AUTHORIZE.name());
            resp.sendRedirect("authorize");
        }else {
            chain.doFilter(request, response);
        }
    }
}
