package com.epam.cashierregister.controllers.servlets.viewServlets;

import com.epam.cashierregister.services.DAO.ChecksDAO;
import com.epam.cashierregister.services.entities.check.Check;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "EditExistingCheckServlet", value = "/editExisting")
public class EditExistingCheckServlet extends HttpServlet {
    private ChecksDAO checksDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        checksDAO = (ChecksDAO) config.getServletContext().getAttribute("ChecksDAO");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Check check = checksDAO.getCheckWithGoods(Integer.parseInt(req.getParameter("edit")));
        if (session.getAttribute("error") == null){
            session.setAttribute("error", " ");
        }
        if (check.getGoodsSet().size() == 0){
            checksDAO.deleteCheck(check);
            resp.sendRedirect("checks");
        }else {
            session.setAttribute("activeCheck", check);
            req.getRequestDispatcher("WEB-INF/view/editCheck.jsp").forward(req, resp);
        }
    }
}
