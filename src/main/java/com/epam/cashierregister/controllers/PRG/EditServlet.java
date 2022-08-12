package com.epam.cashierregister.controllers.PRG;

import com.epam.cashierregister.services.DAO.GoodsDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "EditServlet", value = "/edit")
public class EditServlet extends HttpServlet {
    static Logger LOG = LogManager.getLogger(EditServlet.class);
    private GoodsDAO goodsDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        goodsDAO = (GoodsDAO) config.getServletContext().getAttribute("GoodsDAO");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Change number for id = {} to {}", req.getParameter("id"), req.getParameter("numb"));
        int id = Integer.parseInt(req.getParameter("id"));
        int newNumber = Integer.parseInt(req.getParameter("numb"));

        if (goodsDAO.updateNumber(id, newNumber)) {
            LOG.info("Success update");
        } else {
            LOG.warn("Failed update");
        }

        resp.sendRedirect("goods?edit=true");
    }
}
