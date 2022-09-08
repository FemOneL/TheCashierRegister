package com.epam.cashierregister.services.validateservices;

import com.epam.cashierregister.services.dao.EmployeeDAO;
import com.epam.cashierregister.services.consts.Errors;
import com.epam.cashierregister.services.exeptions.DatabaseException;
import com.epam.cashierregister.services.exeptions.InvalidInputException;

import javax.servlet.http.HttpServletRequest;

/**
 * Method for validation registration input data
 */
public class ValidateSignUp extends ValidateInputService{

    private final EmployeeDAO employeeDAO;

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
            throw new InvalidInputException(Errors.INPUT_NAME_CORRECTLY.name());
        }
        if (!checkEmail()){
            throw new InvalidInputException(Errors.INVALID_EMAIL.name());
        }
        if (!checkValidPassword()){
            throw new InvalidInputException(Errors.INVALID_PASSWORD.name());
        }
        if (!checkPassword()){
            throw new InvalidInputException(Errors.DIFFERENT_PASSWORDS.name());
        }
        if (!checkUniqLogin()){
            throw new InvalidInputException(Errors.ALREADY_REGISTER.name());
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
