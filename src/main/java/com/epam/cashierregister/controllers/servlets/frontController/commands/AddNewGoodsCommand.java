package com.epam.cashierregister.controllers.servlets.frontController.commands;

import com.epam.cashierregister.controllers.servlets.frontController.FrontCommand;
import com.epam.cashierregister.services.DAO.CategoriesDAO;
import com.epam.cashierregister.services.DAO.GoodsDAO;
import com.epam.cashierregister.services.DAO.ProducersDAO;
import com.epam.cashierregister.services.UploadPhotoService;
import com.epam.cashierregister.services.entities.goods.Category;
import com.epam.cashierregister.services.entities.goods.Goods;
import com.epam.cashierregister.services.entities.goods.Producer;
import com.epam.cashierregister.services.exeptions.DatabaseException;
import com.epam.cashierregister.services.exeptions.InvalidInputException;
import com.epam.cashierregister.services.validateServices.ValidateAddGoods;
import com.epam.cashierregister.services.validateServices.ValidateInputService;

import javax.servlet.ServletException;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Command for adding new goods
 */
public class AddNewGoodsCommand extends FrontCommand {
    private CategoriesDAO categoriesDAO;
    private ProducersDAO producersDAO;
    private GoodsDAO goodsDAO;

    @Override
    public void initContext() throws ServletException {
        categoriesDAO = (CategoriesDAO) context.getAttribute("CategoriesDAO");
        producersDAO = (ProducersDAO) context.getAttribute("ProducersDAO");
        goodsDAO = (GoodsDAO) context.getAttribute("GoodsDAO");
    }

    @Override
    public boolean filter() throws ServletException, IOException, DatabaseException {
        ValidateInputService validate = new ValidateAddGoods(req, goodsDAO);
        try {
            validate.validate();
        } catch (InvalidInputException e) {
            LOG.warn("Invalid input: {}", e.getMessage());
            req.getSession().setAttribute("error", e.getMessage());
            redirect("addGoods");
            return false;
        }
        return true;
    }

    @Override
    public void process() throws ServletException, IOException {
        LOG.info("Adding new goods");
        String photo = UploadPhotoService.uploadPhoto(req, "goodsPhotos");
        if (photo == null) {
            photo = "nopicture.png";
        }
        boolean isNewCategory = false;
        boolean isNewProducer = false;
        String model = req.getParameter("model");
        int numbers = Integer.parseInt(req.getParameter("number"));
        BigDecimal cost = BigDecimal.valueOf(Double.parseDouble(req.getParameter("cost") + "." + req.getParameter("cents")));
        Category category;
        Producer producer;
        String selectCategory = req.getParameter("select_category");
        if (!selectCategory.equals("none")) {
            category = new Category(selectCategory);
        } else {
            category = new Category(req.getParameter("new_category"));
            isNewCategory = true;
        }
        String selectProducer = req.getParameter("select_producer");
        if (!selectProducer.equals("none")) {
            producer = new Producer(selectProducer);
        } else {
            producer = new Producer(req.getParameter("new_producer"));
            isNewProducer = true;
        }
        Goods myGoods = new Goods(0, model, ("goodsPhotos/" + photo), numbers, cost, category, producer);
        try {
            if (isNewCategory) {
                categoriesDAO.addNewCategory(category);
            }
            if (isNewProducer) {
                producersDAO.addNewProducer(producer);
            }
            goodsDAO.addGoods(myGoods);
        } catch (DatabaseException e) {
            LOG.error("Problem adding goods");
            req.getSession().setAttribute("javax.servlet.error.status_code", e.getErrorCode());
            redirect("errorPage");
        } catch (InvalidInputException e) {
            LOG.warn("Invalid input: {}", e.getMessage());
            req.getSession().setAttribute("error", e.getMessage());
        }
        redirect("addGoods");
    }


}
