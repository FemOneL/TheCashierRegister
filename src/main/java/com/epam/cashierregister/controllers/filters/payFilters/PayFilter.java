package com.epam.cashierregister.controllers.filters.payFilters;

import com.epam.cashierregister.services.entities.check.Check;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

@WebFilter(filterName = "PayFilter", value = "/pay")
public class PayFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        Check check = (Check) session.getAttribute("activeCheck");
        if (check.getGoodsSet().size() == 0) {
            session.setAttribute("error", "you must add any goods!!");
            resp.sendRedirect("createCheck");
        } else {
            if (session.getAttribute("type") == null) {
                session.setAttribute("type", "none");
            }
            if (session.getAttribute("remainder") == null) {
                session.setAttribute("remainder", new BigDecimal(0));
            }
            if (session.getAttribute("sum") == null) {
                session.setAttribute("sum", new BigDecimal(0));
            }
            chain.doFilter(request, response);
        }
    }
}
