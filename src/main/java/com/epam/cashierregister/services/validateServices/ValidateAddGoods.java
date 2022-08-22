package com.epam.cashierregister.services.validateServices;

import com.epam.cashierregister.services.DAO.GoodsDAO;
import com.epam.cashierregister.services.exeptions.InvalidInputException;

import javax.servlet.http.HttpServletRequest;

public class ValidateAddGoods extends ValidateInputService{
    private GoodsDAO goodsDAO;

    public ValidateAddGoods(String command, HttpServletRequest request, GoodsDAO goodsDAO) {
        super(command, request);
        this.goodsDAO = goodsDAO;
    }

    @Override
    public void validate() throws InvalidInputException {
        if (!command.equals("AddNewGoods")) {
            throw new IllegalArgumentException();
        }
        if (!checkModel()){
            throw new InvalidInputException("Model field cannot be empty!");
        }
        if (!checkSelect()){
            throw new InvalidInputException("Please, select or write new category/producer");
        }
        if (!checkUniq()){
            throw new InvalidInputException("Model must be unique!!");
        }
    }

    private boolean checkModel(){
        return validateField.validateModel(request.getParameter("model"));
    }

    private boolean checkSelect(){
        return validateField.validateSelect(request.getParameter("select_category"), request.getParameter("new_category")) &&
                validateField.validateSelect(request.getParameter("select_producer"), request.getParameter("new_producer"));
    }

    private boolean checkUniq(){
        return goodsDAO.checkModel(request.getParameter("model"));
    }
}
