package com.epam.cashierregister.services.validateServices;

import com.epam.cashierregister.services.DAO.EmployeeDAO;
import com.epam.cashierregister.services.exeptions.InvalidInputException;

import javax.servlet.http.HttpServletRequest;

public class ValidateSignUp extends ValidateInputService{

    private EmployeeDAO employeeDAO;

    public ValidateSignUp(String command, HttpServletRequest request, EmployeeDAO employeeDAO) {
        super(command, request);
        this.employeeDAO = employeeDAO;
    }

    @Override
    public void validate() throws InvalidInputException {
        if (!command.equals("SignUp")) {
            throw new IllegalArgumentException();
        }
        if (!checkName()){
            throw new InvalidInputException("Please input name correctly");
        }
        if (!checkEmail()){
            throw new InvalidInputException("invalid email");
        }
        if (!checkValidPassword()){
            throw new InvalidInputException("invalid password");
        }
        if (!checkPassword()){
            throw new InvalidInputException("password are different");
        }
        if (!checkUniqLogin()){
            throw new InvalidInputException("such a user is already registered");
        }
    }

    private boolean checkName(){
        return validateField.validateName(request.getParameter("firstname"), request.getParameter("secondname"));
    }

    private boolean checkEmail(){
        return validateField.validateEmail(request.getParameter("email"));
    }

    private boolean checkValidPassword(){
        return validateField.validatePassword(request.getParameter("password"));
    }

    private boolean checkPassword(){
        return request.getParameter("password").equals(request.getParameter("secondPassword"));
    }

    private boolean checkUniqLogin(){
        return employeeDAO.getEmployee(request.getParameter("email")) == null;
    }
}
