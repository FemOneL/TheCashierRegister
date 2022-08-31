package com.epam.cashierregister.services.validateServices;

import com.epam.cashierregister.services.DAO.GoodsDAO;
import com.epam.cashierregister.services.exeptions.DatabaseException;
import com.epam.cashierregister.services.exeptions.InvalidInputException;

import javax.servlet.http.HttpServletRequest;


/**
 * Method for validation add goods fields
 */
public class ValidateAddGoods extends ValidateInputService{
    private GoodsDAO goodsDAO;

    /**
     * @param request that contains parameters for validation
     * @param goodsDAO needed for uniq check in <code>checkUniq()</code>
     */
    public ValidateAddGoods(HttpServletRequest request, GoodsDAO goodsDAO) {
        super(request);
        this.goodsDAO = goodsDAO;
    }

    @Override
    public void validate() throws InvalidInputException, DatabaseException {
        if (!checkModel()){
            throw new InvalidInputException("Model field cannot be empty!");
        }
        if (!checkSelect()){
            throw new InvalidInputException("Please, select or write new category/producer");
        }
        if (!checkUniq()){
            throw new InvalidInputException("Model must be unique!!");
        }
        LOG.info("Validate success");
    }

    /**
     * @return true if model input correctly
     */
    private boolean checkModel(){
        return validateField.validateModel(request.getParameter("model"));
    }

    /**
     * @return true if chosen only new or only exist category
     */
    private boolean checkSelect(){
        return validateField.validateSelect(request.getParameter("select_category"), request.getParameter("new_category")) &&
                validateField.validateSelect(request.getParameter("select_producer"), request.getParameter("new_producer"));
    }

    /**
     * @return true if model is uniq
     */
    private boolean checkUniq() throws DatabaseException {
        try {
            return goodsDAO.checkModel(request.getParameter("model"));
        } catch (DatabaseException e) {
            throw new DatabaseException(500);
        }
    }
}
