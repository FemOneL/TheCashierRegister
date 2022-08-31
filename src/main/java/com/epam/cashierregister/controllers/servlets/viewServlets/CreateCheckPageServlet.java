package com.epam.cashierregister.controllers.servlets.viewServlets;

import com.epam.cashierregister.services.entities.check.Check;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(name = "CreateCheckServlet", value = "/createCheck")
public class CreateCheckPageServlet extends HttpServlet {
    static Logger LOG = LogManager.getLogger(CreateCheckPageServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Opened create Check servlet (/createCheck)");
        HttpSession session = req.getSession();
        if (session.getAttribute("activeCheck") == null){
            session.setAttribute("activeCheck", new Check());
        }
        LOG.info("Opened create Check page (createCheck.jsp)");
        req.getRequestDispatcher("WEB-INF/view/createCheck.jsp").forward(req, resp);
    }

}
