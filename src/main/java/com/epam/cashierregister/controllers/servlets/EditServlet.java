package com.epam.cashierregister.controllers.servlets;

import com.epam.cashierregister.model.DAO.GoodsDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "EditServlet", value = "/edit")
public class EditServlet extends HttpServlet {
    static Logger LOG = LogManager.getLogger(EditServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("{} - {}", req.getParameter("id"), req.getParameter("numb"));
        GoodsDAO goodsDAO = GoodsDAO.getInstance();
        int id = Integer.parseInt(req.getParameter("id"));
        int newNumber = Integer.parseInt(req.getParameter("numb"));
        if (newNumber > 0) {
            if (goodsDAO.updateNumber(id, newNumber)){
                LOG.info("Success update");
            }else {
                LOG.warn("Failed update");
            }
        }
        resp.sendRedirect("goods?edit=true");
    }
}
