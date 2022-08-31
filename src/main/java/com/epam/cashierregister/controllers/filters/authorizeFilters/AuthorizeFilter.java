package com.epam.cashierregister.controllers.filters.authorizeFilters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter for invalidated session if anybody sign out
 */
@WebFilter(filterName = "AuthorizeFilter", value = "/authorize")
public class AuthorizeFilter implements Filter {
    static Logger LOG = LogManager.getLogger(AuthorizeFilter.class);

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        if (session.getAttribute("employee") != null){
            LOG.info("session invalidated");
            session.invalidate();
        }
        chain.doFilter(request, response);
    }
}
