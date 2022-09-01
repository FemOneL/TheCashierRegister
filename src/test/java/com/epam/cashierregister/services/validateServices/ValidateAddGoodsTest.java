package com.epam.cashierregister.services.validateServices;

import com.epam.cashierregister.services.DAO.GoodsDAO;
import com.epam.cashierregister.services.consts.Errors;
import com.epam.cashierregister.services.exeptions.DatabaseException;
import com.epam.cashierregister.services.exeptions.InvalidInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ValidateAddGoodsTest {
    private ValidateAddGoods validateAddGoods;
    private HttpServletRequest request;
    private GoodsDAO goodsDAO;

    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
        goodsDAO = mock(GoodsDAO.class);
        validateAddGoods = new ValidateAddGoods(request, goodsDAO);
    }

    @AfterEach
    void tearDown() {
        validateAddGoods = null;
        request = null;
        goodsDAO = null;
    }

    @Test
    void testCheckModelValidate() {
        when(request.getParameter("model")).thenReturn(" ");
        InvalidInputException exception = Assertions.assertThrows(InvalidInputException.class, () -> validateAddGoods.validate());
        Assertions.assertEquals(Errors.MODEL_ERROR.name(), exception.getMessage());
    }

    @Test
    void testCheckSelectValidate() {
        when(request.getParameter("model")).thenReturn("iphone 13");
        when(request.getParameter("select_category")).thenReturn("none");
        when(request.getParameter("new_category")).thenReturn(" ");
        InvalidInputException exception = Assertions.assertThrows(InvalidInputException.class, () -> validateAddGoods.validate());
        Assertions.assertEquals(Errors.CATEGORY_ERROR.name(), exception.getMessage());
    }

    @Test
    void testCheckUniqValidate() throws DatabaseException {
        when(request.getParameter("model")).thenReturn("iphone 13");
        when(request.getParameter("select_category")).thenReturn("none");
        when(request.getParameter("new_category")).thenReturn("AI");
        when(request.getParameter("select_producer")).thenReturn("none");
        when(request.getParameter("new_producer")).thenReturn("huawei");
        when(goodsDAO.checkModel(request.getParameter("model"))).thenReturn(false);
        InvalidInputException exception = Assertions.assertThrows(InvalidInputException.class, () -> validateAddGoods.validate());
        Assertions.assertEquals(Errors.MODEL_UNIQ_ERROR.name(), exception.getMessage());
    }


}