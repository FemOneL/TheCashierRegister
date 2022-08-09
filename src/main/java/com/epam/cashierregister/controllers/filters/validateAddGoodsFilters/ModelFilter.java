package com.epam.cashierregister.controllers.filters.validateAddGoodsFilters;

import com.epam.cashierregister.services.ValidateService;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "ModelFilter")
public class ModelFilter implements Filter {
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
        session.setAttribute("error", " ");
        if (validateService.validateModel(request.getParameter("model"))){
            chain.doFilter(request, response);
        } else {
            session.setAttribute("error", "Model field cannot be empty!");
            resp.sendRedirect("addGoods");
        }
    }
}
