package com.epam.cashierregister.controllers.servlets.viewServlets;

import com.epam.cashierregister.services.entities.check.Check;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(name = "CreateCheckServlet", value = "/createCheck")
public class CreateCheckPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("activeCheck") == null){
            session.setAttribute("activeCheck", new Check());
        }
        if (session.getAttribute("error") == null){
            session.setAttribute("error", " ");
        }
        req.getRequestDispatcher("WEB-INF/view/createCheck.jsp").forward(req, resp);
    }

}
