package com.epam.cashierregister.services.validateservices;

import com.epam.cashierregister.services.exeptions.InvalidInputException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.*;

class ValidateRemainderTest {
    private ValidateInputService remainder;
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
        remainder = new ValidateRemainder(request);
    }

    @AfterEach
    void tearDown() {
        remainder = null;
        request = null;
    }

    @Test
    public void testRemainderValidate() throws InvalidInputException {
        when(request.getParameter("count")).thenReturn("asd");
        Assertions.assertThrows(InvalidInputException.class, () -> remainder.validate());
    }
}