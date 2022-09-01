package com.epam.cashierregister.services.validateServices;

import com.epam.cashierregister.services.consts.Errors;
import com.epam.cashierregister.services.entities.check.Check;
import com.epam.cashierregister.services.exeptions.InvalidInputException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Method for validation if goods in check exist
 */
public class ValidateCloseCheck extends ValidateInputService{

    public ValidateCloseCheck(HttpServletRequest request) {
        super(request);
    }

    @Override
    public void validate() throws InvalidInputException {
        HttpSession session = request.getSession();
        Check check = (Check) session.getAttribute("activeCheck");
        if (check.getGoodsSet().size() == 0){
            throw new InvalidInputException(Errors.MUST_ADD_ANY_GOODS.name());
        }
        LOG.info("Validate success");
    }
}
