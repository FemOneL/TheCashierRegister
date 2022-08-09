package com.epam.cashierregister.controllers.filters.validateAddGoodsFilters;

import com.epam.cashierregister.services.DAO.GoodsDAO;
import com.epam.cashierregister.services.ValidateService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "UniqCheckFilter")
public class UniqCheckFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {

    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        GoodsDAO goodsDAO = GoodsDAO.getInstance();
        if (goodsDAO.checkModel(req.getParameter("model"))) {
            chain.doFilter(request, response);
        }else {
            session.setAttribute("error", "Model must be unique!!");
            resp.sendRedirect("addGoods");
        }

    }
}
