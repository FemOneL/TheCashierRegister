package com.epam.cashierregister.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PasswordHashingServiceTest {

    @Test
    void testHash() {
        String expected = "a25fa69d743d4a9564654c6fe503628d29ddfd029f3238c52712040122903a167847d42263fa73d00114fd1d11b0685a405237250fb8fc0ef323374619d69269";
        String actual = PasswordHashingService.hash("taras123", 1);
        Assertions.assertEquals(expected, actual);
    }
}