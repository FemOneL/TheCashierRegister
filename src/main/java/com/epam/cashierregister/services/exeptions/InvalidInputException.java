package com.epam.cashierregister.services.exeptions;

/**
 * Exception which throws after failed validate checked
 * and receive error message
 */
public class InvalidInputException extends Exception {

    public InvalidInputException(String message) {
        super(message);
    }
}
