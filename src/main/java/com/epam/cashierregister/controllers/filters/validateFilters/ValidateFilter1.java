package com.epam.cashierregister.controllers.filters.validateFilters;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebFilter(filterName = "ValidateFilter", value = "/cabinet")
public class ValidateFilter1 implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        if (session.getAttribute("employee") == null) {
            if (validateLogin((String) session.getAttribute("tempLogin"))
                    && validatePassword((String) session.getAttribute("tempPassword"))) {
                chain.doFilter(request, response);
            } else {
                session.setAttribute("error", "uncorrected input");
                resp.sendRedirect("/cashier/");
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    public boolean validateLogin(String login){
        if (login == null){
            return false;
        }
        Pattern p = Pattern.compile("\\w+@\\w{2,}\\.\\w{2,}");
        Matcher m = p.matcher(login);
        return m.matches();
    }

    public boolean validatePassword(String password){
        if (password == null){
            return false;
        }
        Pattern p = Pattern.compile("\\w{6,}");
        Matcher m = p.matcher(password);
        return m.matches();
    }
}
