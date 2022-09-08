package com.epam.cashierregister.controllers.filters.authorizefilters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter for checking is anybody authorized
 */
@WebFilter(filterName = "AuthorizeCheckFilter", value = "/")
public class AuthorizeCheckFilter implements Filter {
    static Logger LOG = LogManager.getLogger(AuthorizeCheckFilter.class);

    public void init(FilterConfig config) throws ServletException {}

    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        if (session.getAttribute("employee") != null){
            LOG.info("Redirected to cabinet page");
            resp.sendRedirect("cabinet");
        } else {
            LOG.info("Redirected to authorize page");
            resp.sendRedirect("authorize");
        }
    }
}
