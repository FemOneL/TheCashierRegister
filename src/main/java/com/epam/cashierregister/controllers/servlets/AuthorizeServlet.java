package com.epam.cashierregister.controllers.servlets;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AuthorizeServlet", value = "/")
public class AuthorizeServlet extends HttpServlet {
    static Logger LOG = LogManager.getLogger(AuthorizeServlet.class);


    @Override
    public void init() throws ServletException {
        LOG.info("init {}", this.getServletName());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        if (session.getAttribute("error") == null){
            session.setAttribute("error", " ");
        }
        if (session.getAttribute("loc") == null){
            session.setAttribute("loc", "eng");
        }
        try {
            LOG.info("Forward to authorize page");
            req.getRequestDispatcher("WEB-INF/view/authorization.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            LOG.error("Wrong forward to authorize page");
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        HttpSession session = req.getSession();
        session.setAttribute("tempLogin", req.getParameter("login"));
        session.setAttribute("tempPassword", req.getParameter("password"));
        try {
            LOG.info("Redirecting in cabinet page");
            resp.sendRedirect("cabinet");
        } catch (IOException e) {
            LOG.error("Wrong redirect to cabinet page");
            e.printStackTrace();
        }
    }

}
