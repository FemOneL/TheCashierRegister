package com.epam.cashierregister.services.validateServices;

import com.epam.cashierregister.services.exeptions.InvalidInputException;

import javax.servlet.http.HttpServletRequest;

public abstract class ValidateInputService {
    protected HttpServletRequest request;
    protected String command;
    protected ValidateService validateField;


    public ValidateInputService(String command, HttpServletRequest request) {
        this.command = command;
        this.validateField = new ValidateService();
        this.request = request;
    }


    public abstract void validate() throws InvalidInputException;



}
