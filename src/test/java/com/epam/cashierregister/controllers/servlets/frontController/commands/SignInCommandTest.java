package com.epam.cashierregister.controllers.servlets.frontController.commands;

import com.epam.cashierregister.controllers.servlets.frontController.FrontCommand;
import com.epam.cashierregister.services.DAO.EmployeeDAO;
import com.epam.cashierregister.services.ValidateService;
import com.epam.cashierregister.services.consts.Errors;
import com.epam.cashierregister.services.exeptions.DatabaseException;
import com.epam.cashierregister.services.exeptions.InvalidInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

class SignInCommandTest {
    private FrontCommand command;

    @BeforeEach
    void setUp() {
        command = new SignInCommand();
    }

    @AfterEach
    void tearDown() {
        command = null;
    }

    @Test
    void testFilter() throws ServletException, IOException, DatabaseException {
        InvalidInputException e = new InvalidInputException(Errors.INVALID_EMAIL.name());
        EmployeeDAO employeeDAO = mock(EmployeeDAO.class);
        ServletContext servletContext = mock(ServletContext.class);
        when(servletContext.getAttribute("EmployeeDAO")).thenReturn(employeeDAO);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getParameter("login")).thenReturn("tfemyak3gmail.com");
        when(req.getSession()).thenReturn(session);
        command.initCommand(servletContext, req, resp);
        Assertions.assertFalse(command.filter());
        verify(session).setAttribute("error", e.getMessage());
    }
}