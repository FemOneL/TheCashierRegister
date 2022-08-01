package com.epam.cashierregister.controllers.PRG;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ChangeLangServlet", value = "/cabinetloc")
public class ChangeLangServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.setAttribute("loc", req.getParameter("lang"));
        resp.sendRedirect("cabinet");
    }
}
