package com.epam.cashierregister.controllers.servlets.frontController;

import com.epam.cashierregister.controllers.servlets.frontController.commands.ChangeLangCommand;
import com.epam.cashierregister.controllers.servlets.frontController.commands.UnknownCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FrontControllerServletTest {
    private HttpServletRequest request;
    private FrontControllerServlet servlet;


    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
        servlet = new FrontControllerServlet();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void testGetCommand(){
        FrontCommand command = new ChangeLangCommand();
        when(request.getParameter("cmd")).thenReturn("ChangeLang");
        Assertions.assertEquals(command.getClass(), servlet.getCommand(request).getClass());
    }

    @Test
    public void testUnknownCommand(){
        FrontCommand command = new UnknownCommand();
        when(request.getParameter("cmd")).thenReturn("dasdas");
        Assertions.assertEquals(command.getClass(), servlet.getCommand(request).getClass());
    }

    @Test
    public void testNullCommand(){
        FrontCommand command = new UnknownCommand();
        Assertions.assertEquals(command.getClass(), servlet.getCommand(request).getClass());
    }
}