package com.epam.cashierregister.controllers.servlets.viewservlets;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

class ErrorPageServletTest {
    private final String path ="WEB-INF/view/error.jsp";
    private ErrorPageServlet errorPageServlet;
    private HttpServletRequest request;
    private HttpSession session;
    private HttpServletResponse response;
    private RequestDispatcher requestDispatcher;

    @BeforeEach
    void setUp() {
        errorPageServlet = new ErrorPageServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        requestDispatcher = mock(RequestDispatcher.class);

    }

    @AfterEach
    void tearDown() {
        errorPageServlet = null;
        request = null;
        response = null;
        session = null;
        requestDispatcher = null;
    }

    @Test
    void test404() throws ServletException, IOException {
        when(request.getRequestDispatcher(path)).thenReturn(requestDispatcher);
        when(request.getAttribute("javax.servlet.error.status_code")).thenReturn(404);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("javax.servlet.error.status_code")).thenReturn(null);
        errorPageServlet.doGet(request,response);
        verify(requestDispatcher, times(1)).forward(request, response);
        verify(request).setAttribute("errorMessage", "PAGE NOT FOUND");
        verify(request).setAttribute("errorCode", 404);
    }

    @Test
    void test500() throws ServletException, IOException {
        when(request.getRequestDispatcher(path)).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("javax.servlet.error.status_code")).thenReturn(null);
        when(request.getAttribute("javax.servlet.error.status_code")).thenReturn(500);
        errorPageServlet.doGet(request,response);
        verify(requestDispatcher, times(1)).forward(request, response);
        verify(request).setAttribute("errorMessage", "SOMETHING GO WRONG...");
        verify(request).setAttribute("errorCode", 500);
    }
}