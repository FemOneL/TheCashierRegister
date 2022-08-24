package com.epam.cashierregister.controllers.servlets.viewServlets;

import com.epam.cashierregister.controllers.servlets.frontController.FrontCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AuthorizeServlet", value = "/authorize")
public class AuthorizeServlet extends HttpServlet {
    protected static Logger LOG = LogManager.getLogger(AuthorizeServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("INFO");
        LOG.debug("DEBUG");
        LOG.error("ERROR");
        HttpSession session = req.getSession();
        if (session.getAttribute("error") == null) {
            session.setAttribute("error", " ");
        }
        req.getRequestDispatcher("WEB-INF/view/authorization.jsp").forward(req, resp);
    }

}
