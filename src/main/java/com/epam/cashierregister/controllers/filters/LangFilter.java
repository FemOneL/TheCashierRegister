package com.epam.cashierregister.controllers.filters;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter for choose default language and set utf-8
 */
@WebFilter(filterName = "LangFilter", value = "/*")
public class LangFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        request.setCharacterEncoding("UTF-8");
        if (session.getAttribute("loc") == null){
            session.setAttribute("loc", "eng");
        }
        chain.doFilter(request, response);
    }
}
