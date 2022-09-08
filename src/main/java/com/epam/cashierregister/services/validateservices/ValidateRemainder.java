package com.epam.cashierregister.services.validateservices;

import com.epam.cashierregister.services.consts.Errors;
import com.epam.cashierregister.services.exeptions.InvalidInputException;

import javax.servlet.http.HttpServletRequest;

/**
 * Method for validation remainder field
 */
public class ValidateRemainder extends ValidateInputService {

    public ValidateRemainder(HttpServletRequest request) {
        super(request);
    }

    @Override
    public void validate() throws InvalidInputException {
        if (!validateField.validateCostField(request.getParameter("count"))) {
            throw new InvalidInputException(Errors.WRONG_INPUT.name());
        }
        LOG.info("Validate success");
    }
}
