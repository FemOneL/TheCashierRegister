package com.epam.cashierregister.controllers.filters.validateAuthorizeFilters;

import com.epam.cashierregister.services.ValidateService;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebFilter(filterName = "ValidateEmail")
public class ValidateEmailFilter implements Filter {
    private ValidateService validateService;

    public void init(FilterConfig config) throws ServletException {
        validateService = (ValidateService) config.getServletContext().getAttribute("validateService");
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        if (session.getAttribute("employee") == null) {
            if (validateService.validateEmail((String) session.getAttribute("tempLogin"))) {
                chain.doFilter(request, response);
            } else {
                session.setAttribute("error", "invalid login");
                resp.sendRedirect("/cashier/");
            }
        } else {
            chain.doFilter(request, response);
        }
    }



}
