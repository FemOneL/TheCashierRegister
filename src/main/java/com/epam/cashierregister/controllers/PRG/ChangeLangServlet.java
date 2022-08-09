package com.epam.cashierregister.controllers.PRG;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ChangeLangServlet", value = "/changeLang")
public class ChangeLangServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("lang") != null) {
            HttpSession session = req.getSession();
            session.setAttribute("loc", req.getParameter("lang"));
        }
        resp.sendRedirect("cabinet");
    }
}
