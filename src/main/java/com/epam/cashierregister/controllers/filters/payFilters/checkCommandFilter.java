package com.epam.cashierregister.controllers.filters.payFilters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "checkCommandFilter", value = "/pay")
public class checkCommandFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        if (request.getParameter("command") != null && request.getParameter("command").equals("cancel")) {
            session.removeAttribute("activeCheck");
            resp.sendRedirect("createCheck");
        } else {
            chain.doFilter(request, response);
        }
    }
}