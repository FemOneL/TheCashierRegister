package com.epam.cashierregister.controllers.servlets.viewservlets;

import org.junit.jupiter.api.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

class CabinetServletTest {
    private final static String path = "WEB-INF/view/cabinet.jsp";
    private CabinetServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher requestDispatcher;
    private HttpSession session;

    @BeforeEach
    public void setUp(){
        servlet = new CabinetServlet();
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
    public void testCabinetDoGet() throws IOException, ServletException {
        when(request.getRequestDispatcher(path)).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);

        servlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher(path);
        verify(requestDispatcher).forward(request, response);
        verify(session).removeAttribute("error");
    }

}