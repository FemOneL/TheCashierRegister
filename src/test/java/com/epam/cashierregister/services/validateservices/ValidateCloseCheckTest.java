package com.epam.cashierregister.services.validateservices;

import com.epam.cashierregister.services.consts.Errors;
import com.epam.cashierregister.services.entities.check.Check;
import com.epam.cashierregister.services.exeptions.InvalidInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashSet;

import static org.mockito.Mockito.*;

class ValidateCloseCheckTest {
    private ValidateCloseCheck validateCloseCheck;
    private HttpServletRequest request;
    private HttpSession session;

    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        validateCloseCheck = new ValidateCloseCheck(request);
    }

    @AfterEach
    void tearDown() {
        request = null;
        validateCloseCheck = null;
    }

    @Test
    void testValidate() {
        Check check = new Check();
        check.setGoodsSet(new HashSet<>());
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("activeCheck")).thenReturn(check);
        InvalidInputException exception = Assertions.assertThrows(InvalidInputException.class, () -> validateCloseCheck.validate());
        Assertions.assertEquals(Errors.MUST_ADD_ANY_GOODS.name(), exception.getMessage());
    }
}