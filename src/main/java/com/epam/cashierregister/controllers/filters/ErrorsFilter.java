package com.epam.cashierregister.controllers.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "ErrorsFilter", value = {"/goods", "/addGoods", "/authorize",
        "/createCheck", "/signup", "/pay"})
public class ErrorsFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        if (session.getAttribute("error") == null) {
            req.setAttribute("error", " ");
        } else {
            req.setAttribute("error", session.getAttribute("error"));
            session.removeAttribute("error");
        }
        chain.doFilter(request, response);
    }
}
