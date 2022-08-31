package com.epam.cashierregister.services.validateServices;

import com.epam.cashierregister.services.DAO.EmployeeDAO;
import com.epam.cashierregister.services.exeptions.DatabaseException;
import com.epam.cashierregister.services.exeptions.InvalidInputException;

import javax.servlet.http.HttpServletRequest;

/**
 * Method for validation registration input data
 */
public class ValidateSignUp extends ValidateInputService{

    private EmployeeDAO employeeDAO;

    /**
     * @param request that contains parameters for validation
     * @param employeeDAO needed for check login uniq in <code>checkUniqLogin()</code>
     */
    public ValidateSignUp(HttpServletRequest request, EmployeeDAO employeeDAO) {
        super(request);
        this.employeeDAO = employeeDAO;
    }

    @Override
    public void validate() throws InvalidInputException, DatabaseException {
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
            throw new InvalidInputException("passwords are different");
        }
        if (!checkUniqLogin()){
            throw new InvalidInputException("such a user is already registered");
        }
        LOG.info("Validate success");
    }

    /**
     * @return true if name input correctly
     */
    private boolean checkName(){
        return validateField.validateName(request.getParameter("firstname"), request.getParameter("secondname"));
    }

    /**
     * @return true if email input correctly
     */
    private boolean checkEmail(){
        return validateField.validateEmail(request.getParameter("email"));
    }

    /**
     * @return true if password input correctly
     */
    private boolean checkValidPassword(){
        return validateField.validatePassword(request.getParameter("password"));
    }


    /**
     * @return true if password in both fields repeated
     */
    private boolean checkPassword(){
        return request.getParameter("password").equals(request.getParameter("secondPassword"));
    }


    /**
     * @return true if login is uniq
     * @throws DatabaseException if something go wrong with database
     */
    private boolean checkUniqLogin() throws DatabaseException {
        try {
            return employeeDAO.getEmployee(request.getParameter("email")) == null;
        } catch (DatabaseException e) {
            throw new DatabaseException(500);
        }
    }
}
