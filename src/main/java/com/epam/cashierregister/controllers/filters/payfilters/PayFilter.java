package com.epam.cashierregister.controllers.filters.payfilters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Filter for clearing not needed attributes before /pay
 */
@WebFilter(filterName = "PayFilter", value = "/pay")
public class PayFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        if (session.getAttribute("type") == null) {
            session.setAttribute("type", "none");
        }
        if (session.getAttribute("remainder") == null) {
            session.setAttribute("remainder", new BigDecimal(0));
        }
        if (session.getAttribute("payAnother") == null) {
            session.setAttribute("payAnother", "0");
        }
        if (session.getAttribute("sum") == null) {
            session.setAttribute("sum", new BigDecimal(0));
        }
        chain.doFilter(request, response);
    }
}
