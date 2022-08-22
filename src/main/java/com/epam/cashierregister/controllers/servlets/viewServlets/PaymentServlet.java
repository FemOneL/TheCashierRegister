package com.epam.cashierregister.controllers.servlets.viewServlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "PaymentServlet", value = "/pay")
public class PaymentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("error") == null){
            session.setAttribute("error", " ");
        }
        req.getRequestDispatcher("WEB-INF/view/pay.jsp").forward(req, resp);
    }
}
