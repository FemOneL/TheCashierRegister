package com.epam.cashierregister.controllers.servlets.viewservlets;

import com.epam.cashierregister.services.entities.check.Check;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet for create check page view
 */
@WebServlet(name = "CreateCheckServlet", value = "/createCheck")
public class CreateCheckPageServlet extends HttpServlet {
    static Logger LOG = LogManager.getLogger(CreateCheckPageServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Opened create Check servlet (/createCheck)");
        HttpSession session = req.getSession();
        if (session.getAttribute("activeCheck") == null) {
            session.setAttribute("activeCheck", new Check());
        }
        LOG.info("Opened create Check page (createCheck.jsp)");
        req.getRequestDispatcher("WEB-INF/view/createCheck.jsp").forward(req, resp);
    }

}
