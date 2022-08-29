package com.epam.cashierregister.services.validateServices;

import com.epam.cashierregister.services.DAO.EmployeeDAO;
import com.epam.cashierregister.services.PasswordHashierService;
import com.epam.cashierregister.services.entities.employee.Employee;
import com.epam.cashierregister.services.exeptions.InvalidInputException;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class ValidateSignIn extends ValidateInputService {
    private EmployeeDAO employeeDAO;

    public ValidateSignIn(HttpServletRequest request, EmployeeDAO employeeDAO) {
        super(request);
        this.employeeDAO = employeeDAO;
    }

    @Override
    public void validate() throws InvalidInputException {
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
        Employee myEmployee = employeeDAO.getEmployee(request.getParameter("login"));
        if (myEmployee == null) {
            return false;
        }
        String password = PasswordHashierService.hash(request.getParameter("password"), myEmployee.getId());
        if (myEmployee.getAuthorize().getPassword().equals(password)) {
            myEmployee.getAuthorize().setPassword(null);
            request.getSession().setAttribute("employee", myEmployee);
            return true;
        }
        return false;
    }

    private boolean checkPassword() {
        return validateField.validatePassword(request.getParameter("password"));
    }

    private boolean checkEmail() {
        return validateField.validateEmail(request.getParameter("login"));
    }
}
