package com.epam.cashierregister.controllers.servlets.viewServlets;

import com.epam.cashierregister.services.DAO.EmployeeDAO;
import com.epam.cashierregister.services.exeptions.DatabaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SignUpServlet", value = "/signup")
public class SignUpServlet extends HttpServlet {
    private EmployeeDAO employeeDAO;
    static Logger LOG = LogManager.getLogger(SignUpServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        LOG.info("Opened sign up servlet (/signup)");
        employeeDAO = (EmployeeDAO) config.getServletContext().getAttribute("EmployeeDAO");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        try {
            req.setAttribute("roles", employeeDAO.getRoles());
        } catch (DatabaseException e) {
            LOG.error("Problem with sign up");
            req.getSession().setAttribute("javax.servlet.error.status_code", e.getErrorCode());
            resp.sendRedirect("errorPage");
        }
        LOG.info("Opened sign up page (registration.jsp)");
        req.getRequestDispatcher("WEB-INF/view/registration.jsp").forward(req, resp);
    }
}
