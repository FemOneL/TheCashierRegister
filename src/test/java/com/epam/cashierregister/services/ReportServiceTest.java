package com.epam.cashierregister.services;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class ReportServiceTest {
    private static ReportService reportService;

    @BeforeAll
    static void setUp() {
        reportService = new ReportService();
    }

    @AfterAll
    static void tearDown() {
        reportService = null;
    }

    @Test
    void testAddSelling() {
        reportService.addSelling(2, new BigDecimal(123));
        Assertions.assertEquals(reportService.getReport().getSelling().getNumberOfSellingChecks(), 2);
        Assertions.assertEquals(reportService.getReport().getSelling().getSellingSum(), new BigDecimal(123));
    }

    @Test
    void testAddReturned() {
        reportService.addReturned(3, new BigDecimal(231));
        Assertions.assertEquals(reportService.getReport().getReturned().getNumberOfReturningGoods(), 3);
        Assertions.assertEquals(reportService.getReport().getReturned().getReturnedSum(), new BigDecimal(231));
    }

    @Test
    void invalidate() {
        reportService.addSelling(2, new BigDecimal(123));
        Assertions.assertTrue(reportService.getReport().getSelling().getNumberOfSellingChecks() == 2);
        reportService.invalidate();
        Assertions.assertTrue(reportService.getReport().getSelling().getNumberOfSellingChecks() == 0);

    }
}