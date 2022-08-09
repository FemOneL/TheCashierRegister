package com.epam.cashierregister.controllers.filters.validateAddGoodsFilters;

import com.epam.cashierregister.services.ValidateService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "SelectsFilter")
public class SelectsFilter implements Filter {
    private ValidateService validateService;

    public void init(FilterConfig config) throws ServletException {
        validateService = (ValidateService) config.getServletContext().getAttribute("validateService");

    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        if (validateService.validateSelect(request.getParameter("select_category"), request.getParameter("new_category"))
                && validateService.validateSelect(request.getParameter("select_producer"), request.getParameter("new_producer"))){
            chain.doFilter(request, response);
        } else {
            session.setAttribute("error", "Please, select or write new category/producer");
            resp.sendRedirect("addGoods");
        }
    }
}
