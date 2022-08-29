package com.epam.cashierregister.controllers.servlets.viewServlets;

import com.epam.cashierregister.services.DAO.CategoriesDAO;
import com.epam.cashierregister.services.DAO.ProducersDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

class AddGoodsPageServletTest {
    private final static String path = "WEB-INF/view/addGoods.jsp";
    private AddGoodsPageServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher requestDispatcher;
    private HttpSession session;
    private ServletConfig servletConfig;
    private ServletContext servletContext;

    @BeforeEach
    public void setUp() {
        servlet = new AddGoodsPageServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        requestDispatcher = mock(RequestDispatcher.class);
        session = mock(HttpSession.class);
        servletConfig = mock(ServletConfig.class);
        servletContext = mock(ServletContext.class);
    }

    @AfterEach
    public void tearDown() {
        request = null;
        response = null;
        requestDispatcher = null;
        session = null;
        servlet = null;
        servletConfig = null;
        servletContext = null;
    }

    @Test
    void testaddGoodsPageDoGet() throws ServletException, IOException {
        CategoriesDAO categoriesDAO = mock(CategoriesDAO.class);
        ProducersDAO producersDAO = mock(ProducersDAO.class);
        servletConfig = mock(ServletConfig.class);
        servletContext = mock(ServletContext.class);

        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("CategoriesDAO")).thenReturn(categoriesDAO);
        when(servletContext.getAttribute("ProducersDAO")).thenReturn(producersDAO);

        when(request.getRequestDispatcher(path)).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        servlet.init(servletConfig);
        servlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher(path);
        verify(requestDispatcher).forward(request, response);
        verify(session).setAttribute("error", " ");
        verify(request).setAttribute("categories", categoriesDAO.getCategoryList());
        verify(request).setAttribute("producers", producersDAO.getProducerList());
    }
}