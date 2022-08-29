package com.epam.cashierregister.services.validateServices;

import com.epam.cashierregister.services.DAO.EmployeeDAO;
import com.epam.cashierregister.services.entities.employee.Employee;
import com.epam.cashierregister.services.exeptions.InvalidInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ValidateSignUpTest {
    private ValidateSignUp validateSignUp;
    private HttpServletRequest request;
    private EmployeeDAO employeeDAO;


    @BeforeEach
    void setUp() {
        employeeDAO = mock(EmployeeDAO.class);
        request = mock(HttpServletRequest.class);
        validateSignUp = new ValidateSignUp(request, employeeDAO);
    }

    @AfterEach
    void tearDown() {
        employeeDAO = null;
        request = null;
        validateSignUp = null;
    }

    @Test
    void testCheckName() {
        when(request.getParameter("firstname")).thenReturn("L");
        when(request.getParameter("secondname")).thenReturn("L");
        InvalidInputException exception = Assertions.assertThrows(InvalidInputException.class, () -> validateSignUp.validate());
        Assertions.assertEquals("Please input name correctly", exception.getMessage());
    }

    @Test
    void testCheckEmail() {
        when(request.getParameter("firstname")).thenReturn("Taras");
        when(request.getParameter("secondname")).thenReturn("Femiak");
        when(request.getParameter("email")).thenReturn("L");
        InvalidInputException exception = Assertions.assertThrows(InvalidInputException.class, () -> validateSignUp.validate());
        Assertions.assertEquals("invalid email", exception.getMessage());
    }

    @Test
    void testCheckValidPassword() {
        when(request.getParameter("firstname")).thenReturn("Taras");
        when(request.getParameter("secondname")).thenReturn("Femiak");
        when(request.getParameter("email")).thenReturn("tfemyak@gmail.com");
        when(request.getParameter("password")).thenReturn("ta");
        InvalidInputException exception = Assertions.assertThrows(InvalidInputException.class, () -> validateSignUp.validate());
        Assertions.assertEquals("invalid password", exception.getMessage());
    }

    @Test
    void testCheckPassword() {
        when(request.getParameter("firstname")).thenReturn("Taras");
        when(request.getParameter("secondname")).thenReturn("Femiak");
        when(request.getParameter("email")).thenReturn("tfemyak@gmail.com");
        when(request.getParameter("password")).thenReturn("taras123");
        when(request.getParameter("secondPassword")).thenReturn("taras12");
        InvalidInputException exception = Assertions.assertThrows(InvalidInputException.class, () -> validateSignUp.validate());
        Assertions.assertEquals("passwords are different", exception.getMessage());
    }

    @Test
    void testCheckUniqLogin() {
        when(request.getParameter("firstname")).thenReturn("Taras");
        when(request.getParameter("secondname")).thenReturn("Femiak");
        when(request.getParameter("email")).thenReturn("tfemyak@gmail.com");
        when(request.getParameter("password")).thenReturn("taras123");
        when(request.getParameter("secondPassword")).thenReturn("taras123");
        when(employeeDAO.getEmployee(request.getParameter("email"))).thenReturn(new Employee());
        InvalidInputException exception = Assertions.assertThrows(InvalidInputException.class, () -> validateSignUp.validate());
        Assertions.assertEquals("such a user is already registered", exception.getMessage());
    }
}
