package com.epam.cashierregister.services.exeptions;

public class InvalidInputException extends Exception {

    public InvalidInputException(String message) {
        super(message);
    }
}
