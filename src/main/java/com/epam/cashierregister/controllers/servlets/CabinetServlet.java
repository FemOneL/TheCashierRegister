package com.epam.cashierregister.controllers.servlets;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "cabinetServlet", value = "/cabinet")
public class CabinetServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        session.removeAttribute("error");
        if (session.getAttribute("loc") == null){
            session.setAttribute("loc", "eng");
        }
        req.getRequestDispatcher("WEB-INF/view/cabinet.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("lang") != null){
            resp.sendRedirect("cabinetloc?lang=" + req.getParameter("lang"));
        }else{
            doGet(req, resp);
        }
    }
}