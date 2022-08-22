package com.epam.cashierregister.services.validateServices;

import com.epam.cashierregister.services.DAO.EmployeeDAO;
import com.epam.cashierregister.services.entities.employee.Employee;
import com.epam.cashierregister.services.exeptions.InvalidInputException;

import javax.servlet.http.HttpServletRequest;

public class ValidateSignIn extends ValidateInputService {
    private EmployeeDAO employeeDAO;

    public ValidateSignIn(String command, HttpServletRequest request, EmployeeDAO employeeDAO) {
        super(command, request);
        this.employeeDAO = employeeDAO;
    }

    @Override
    public void validate() throws InvalidInputException {
        if (!command.equals("SignIn")) {
            throw new IllegalArgumentException();
        }
        if (!checkEmail()) {
            throw new InvalidInputException("invalid login");
        }
        if (!checkPassword()) {
            throw new InvalidInputException("invalid password");
        }
        if (!checkData()){
            throw new InvalidInputException("employee not found");
        }
    }

    private boolean checkData() {
        Employee myEmployee = employeeDAO.getEmployee(request.getParameter("login"), request.getParameter("password"));
        if (myEmployee == null) {
            return false;
        }
        myEmployee.setAuthorize(null);
        request.getSession().setAttribute("employee", myEmployee);
        return true;
    }

    private boolean checkPassword() {
        return validateField.validatePassword(request.getParameter("password"));
    }

    private boolean checkEmail() {
        return validateField.validateEmail(request.getParameter("login"));
    }
}
