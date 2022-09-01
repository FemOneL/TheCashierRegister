package com.epam.cashierregister.services.validateServices;

import com.epam.cashierregister.services.DAO.EmployeeDAO;
import com.epam.cashierregister.services.PasswordHashingService;
import com.epam.cashierregister.services.consts.Errors;
import com.epam.cashierregister.services.entities.employee.Employee;
import com.epam.cashierregister.services.exeptions.DatabaseException;
import com.epam.cashierregister.services.exeptions.InvalidInputException;

import javax.servlet.http.HttpServletRequest;

/**
 * Method for validation authorization input data
 */
public class ValidateSignIn extends ValidateInputService {
    private EmployeeDAO employeeDAO;

    /**
     * @param request that contains parameters for validation
     * @param employeeDAO needed for check data in <code>checkData()</code>
     */
    public ValidateSignIn(HttpServletRequest request, EmployeeDAO employeeDAO) {
        super(request);
        this.employeeDAO = employeeDAO;
    }

    @Override
    public void validate() throws InvalidInputException, DatabaseException {
        if (!checkEmail()) {
            throw new InvalidInputException(Errors.INVALID_EMAIL.name());
        }
        if (!checkPassword()) {
            throw new InvalidInputException(Errors.INVALID_PASSWORD.name());
        }
        if (!checkData()) {
            throw new InvalidInputException(Errors.EMP_NOT_FOUND.name());
        }
        LOG.info("Validate success");
    }

    /**
     * @return true if employee exist in database
     * @throws DatabaseException if something go wrong with database
     */
    private boolean checkData() throws DatabaseException {
        Employee myEmployee;
        try {
            myEmployee = employeeDAO.getEmployee(request.getParameter("login"));
        } catch (DatabaseException e) {
            throw new DatabaseException(500);
        }
        if (myEmployee == null) {
            return false;
        }
        String password = PasswordHashingService.hash(request.getParameter("password"), myEmployee.getId());
        if (myEmployee.getAuthorize().getPassword().equals(password)) {
            myEmployee.getAuthorize().setPassword(null);
            request.getSession().setAttribute("employee", myEmployee);
            return true;
        }
        return false;
    }

    /**
     * @return true if password input correctly
     */
    private boolean checkPassword() {
        return validateField.validatePassword(request.getParameter("password"));
    }

    /**
     * @return true if email input correctly
     */
    private boolean checkEmail() {
        return validateField.validateEmail(request.getParameter("login"));
    }
}
