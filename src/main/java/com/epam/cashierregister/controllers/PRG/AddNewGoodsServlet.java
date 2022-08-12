package com.epam.cashierregister.controllers.PRG;

import com.epam.cashierregister.services.DAO.CategoriesDAO;
import com.epam.cashierregister.services.DAO.GoodsDAO;
import com.epam.cashierregister.services.DAO.ProducersDAO;
import com.epam.cashierregister.services.entities.goods.Category;
import com.epam.cashierregister.services.entities.goods.Goods;
import com.epam.cashierregister.services.entities.goods.Producer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;

@WebServlet(name = "AddNewGoodsServlet", value = "/AddNewGoods")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class AddNewGoodsServlet extends HttpServlet {
    static Logger LOG = LogManager.getLogger(EditServlet.class);
    private CategoriesDAO categoriesDAO;
    private ProducersDAO producersDAO;
    private GoodsDAO goodsDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        categoriesDAO = (CategoriesDAO) config.getServletContext().getAttribute("CategoriesDAO");
        producersDAO = (ProducersDAO) config.getServletContext().getAttribute("ProducersDAO");
        goodsDAO = (GoodsDAO) config.getServletContext().getAttribute("GoodsDAO");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        //todo urf8
        String photo = uploadPhoto(req);
        boolean isNewCategory = false;
        boolean isNewProducer = false;
        String model = req.getParameter("model");
        int numbers = Integer.parseInt(req.getParameter("number"));
        BigDecimal cost = BigDecimal.valueOf(Double.parseDouble(req.getParameter("cost") + "." + req.getParameter("cents")));
        Category category;
        Producer producer;
        String selectCategory = req.getParameter("select_category");
        if (!selectCategory.equals("none")){
            category = new Category(selectCategory);
        }else {
            category = new Category(req.getParameter("new_category"));
            isNewCategory = true;
        }
        String selectProducer = req.getParameter("select_producer");
        if (!selectProducer.equals("none")){
            producer = new Producer(selectProducer);
        }else {
            producer = new Producer(req.getParameter("new_producer"));
            isNewProducer = true;
        }
        Goods myGoods = new Goods(0, model, photo, numbers, cost, category, producer);
        if (isNewCategory){
            categoriesDAO.addNewCategory(category);
        }
        if (isNewProducer){
            producersDAO.addNewProducer(producer);
        }
        goodsDAO.addGoods(myGoods);
        resp.sendRedirect("addGoods");
    }


    private String uploadPhoto(HttpServletRequest req) throws ServletException, IOException {
        String name = null;
        Collection<Part> parts = req.getParts();
        String realPath = req.getServletContext().getRealPath("images/goodsPhotos");
        for (Part part : parts) {
            try {
                if (part.getSubmittedFileName() != null) {
                    LOG.info("try upload image in {}", realPath);
                    part.write(realPath + "/" + part.getSubmittedFileName());
                    LOG.info("upload image {}", part.getSubmittedFileName());
                    name = part.getSubmittedFileName();
                }
            }catch (IOException e){
                LOG.error("Can not upload image {} ", part.getSubmittedFileName());
                return null;
            }
        }
        return name;
    }
}
