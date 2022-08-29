package com.epam.cashierregister.services.validateServices;

import com.epam.cashierregister.services.DAO.EmployeeDAO;
import com.epam.cashierregister.services.entities.employee.Employee;
import com.epam.cashierregister.services.exeptions.InvalidInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ValidateSignInTest {
    private ValidateSignIn validateSignIn;
    private HttpServletRequest request;
    private EmployeeDAO employeeDAO;


    @BeforeEach
    void setUp() {
        employeeDAO = mock(EmployeeDAO.class);
        request = mock(HttpServletRequest.class);
        validateSignIn = new ValidateSignIn(request, employeeDAO);
    }

    @AfterEach
    void tearDown() {
        employeeDAO = null;
        request = null;
        validateSignIn = null;
    }

    @Test
    void testCheckEmail() {
        when(request.getParameter("login")).thenReturn("tfemyak@gmail");
        InvalidInputException exception = Assertions.assertThrows(InvalidInputException.class, () -> validateSignIn.validate());
        Assertions.assertEquals("invalid login", exception.getMessage());
    }

    @Test
    void testCheckPassword() {
        when(request.getParameter("login")).thenReturn("tfemyak@gmail.com");
        when(request.getParameter("password")).thenReturn("tar");
        InvalidInputException exception = Assertions.assertThrows(InvalidInputException.class, () -> validateSignIn.validate());
        Assertions.assertEquals("invalid password", exception.getMessage());
    }

    @Test
    void testCheckData() {
        when(request.getParameter("login")).thenReturn("tfemyak@gmail.com");
        when(request.getParameter("password")).thenReturn("taras123");
        when(employeeDAO.getEmployee(request.getParameter("login"))).thenReturn(null);
        InvalidInputException exception = Assertions.assertThrows(InvalidInputException.class, () -> validateSignIn.validate());
        Assertions.assertEquals("employee not found", exception.getMessage());
    }
}