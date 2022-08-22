package com.epam.cashierregister.services.validateServices;

import com.epam.cashierregister.services.entities.check.Check;
import com.epam.cashierregister.services.exeptions.InvalidInputException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ValidateCloseCheck extends ValidateInputService{

    public ValidateCloseCheck(String command, HttpServletRequest request) {
        super(command, request);
    }

    @Override
    public void validate() throws InvalidInputException {
        HttpSession session = request.getSession();
        Check check = (Check) session.getAttribute("activeCheck");
        if (check.getGoodsSet().size() == 0){
            throw new InvalidInputException("you must add any goods!!");
        }
    }
}
