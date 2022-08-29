package com.epam.cashierregister.controllers.servlets.viewServlets;

import com.epam.cashierregister.services.DAO.ChecksDAO;
import com.epam.cashierregister.services.entities.check.Check;
import com.epam.cashierregister.services.entities.goods.Goods;
import org.junit.jupiter.api.AfterEach;
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
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EditExistingCheckServletTest {
    private final static String path = "WEB-INF/view/editCheck.jsp";
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher requestDispatcher;
    private HttpSession session;
    private ServletConfig servletConfig = mock(ServletConfig.class);
    private ServletContext servletContext = mock(ServletContext.class);

    @BeforeEach
    public void setUp() {
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
        servletConfig = null;
        servletContext = null;
    }


    @Test
    void testEditExitingCheckDoGet() throws ServletException, IOException {
        EditExistingCheckServlet servlet = new EditExistingCheckServlet();
        ChecksDAO checksDAO = mock(ChecksDAO.class);
        Check check = mock(Check.class);
        Set<Goods> goodsSet = mock(Set.class);
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("ChecksDAO")).thenReturn(checksDAO);

        when(request.getRequestDispatcher(path)).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);

        when(request.getParameter("edit")).thenReturn("1");
        when(check.getGoodsSet()).thenReturn(goodsSet);
        when(goodsSet.size()).thenReturn(1);
        when(checksDAO.getCheckWithGoods(Integer.parseInt(request.getParameter("edit")))).thenReturn(check);
        servlet.init(servletConfig);
        servlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher(path);
        verify(requestDispatcher).forward(request, response);

        when(goodsSet.size()).thenReturn(0);
        doNothing().when(response).sendRedirect("checks");

        servlet.init(servletConfig);
        servlet.doGet(request, response);

        verify(response).sendRedirect("checks");
    }
}