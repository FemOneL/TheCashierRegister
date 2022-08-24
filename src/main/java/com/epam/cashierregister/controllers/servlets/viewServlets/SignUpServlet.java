package com.epam.cashierregister.controllers.servlets.viewServlets;

import com.epam.cashierregister.services.DAO.EmployeeDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SignUpServlet", value = "/signup")
public class SignUpServlet extends HttpServlet {
    private EmployeeDAO employeeDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        employeeDAO = (EmployeeDAO) config.getServletContext().getAttribute("EmployeeDAO");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        req.setAttribute("roles", employeeDAO.getRoles());
        if (session.getAttribute("error") == null){
            session.setAttribute("error", " ");
        }
        req.getRequestDispatcher("WEB-INF/view/registration.jsp").forward(req, resp);
    }
}
