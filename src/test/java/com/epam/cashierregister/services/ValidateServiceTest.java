package com.epam.cashierregister.services;

import org.apache.logging.log4j.core.util.NameUtil;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ValidateServiceTest {
    private static ValidateService validateService;

    @BeforeAll
    public static void setUp(){
        validateService = new ValidateService();
    }

    @AfterAll
    public static void tearDown(){
        validateService = null;
    }


    @Test
    void testValidateEmail() {
        Assertions.assertTrue(validateService.validateEmail("tfemyak@gmail.com"));
        Assertions.assertTrue(validateService.validateEmail("tfemyak@ukr.net"));
        Assertions.assertFalse(validateService.validateEmail("tfemyakgmail.com"));
        Assertions.assertFalse(validateService.validateEmail(null));
    }

    @Test
    void testValidatePassword() {
        Assertions.assertTrue(validateService.validatePassword("asddfef1"));
        Assertions.assertTrue(validateService.validatePassword("naghf123"));
        Assertions.assertFalse(validateService.validatePassword("naf"));
        Assertions.assertFalse(validateService.validatePassword(null));
    }

    @Test
    void testValidateSelect() {
        Assertions.assertTrue(validateService.validateSelect("none", "apple"));
        Assertions.assertTrue(validateService.validateSelect("apple", ""));
        Assertions.assertFalse(validateService.validateSelect("none", ""));
        Assertions.assertFalse(validateService.validateSelect(null, null));
        Assertions.assertFalse(validateService.validateSelect("none", null));
    }

    @Test
    void testValidateName() {
        Assertions.assertTrue(validateService.validateName("Taras", "Femiak"));
        Assertions.assertFalse(validateService.validateName("Taras", "F"));
        Assertions.assertFalse(validateService.validateName("Taras", null));
        Assertions.assertFalse(validateService.validateName(null, null));
    }

    @Test
    void testValidateCostField() {
        Assertions.assertTrue(validateService.validateCostField("231.43"));
        Assertions.assertTrue(validateService.validateCostField("231"));
        Assertions.assertFalse(validateService.validateCostField("DasASD"));
        Assertions.assertFalse(validateService.validateCostField(null));

    }

    @Test
    void testValidateModel() {
        Assertions.assertTrue(validateService.validateModel("Hds123"));
        Assertions.assertFalse(validateService.validateModel("  "));
        Assertions.assertFalse(validateService.validateModel(null));
    }
}