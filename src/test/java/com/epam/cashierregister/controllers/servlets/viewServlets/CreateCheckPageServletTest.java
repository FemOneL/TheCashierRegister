package com.epam.cashierregister.controllers.servlets.viewServlets;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateCheckPageServletTest {
    private final static String path = "WEB-INF/view/createCheck.jsp";
    private CreateCheckPageServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher requestDispatcher;
    private HttpSession session;

    @BeforeEach
    public void setUp(){
        servlet = new CreateCheckPageServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        requestDispatcher = mock(RequestDispatcher.class);
        session = mock(HttpSession.class);
    }

    @AfterEach
    public void tearDown(){
        request = null;
        response = null;
        requestDispatcher = null;
        session = null;
        servlet = null;
    }


    @Test
    void testCreateCheckPageDoGet() throws ServletException, IOException {
        when(request.getRequestDispatcher(path)).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);

        servlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher(path);
        verify(requestDispatcher).forward(request, response);
    }
}