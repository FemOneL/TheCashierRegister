package com.epam.cashierregister.services.exeptions;

/**
 * Exception which throws after database error
 * and receive error code
 */
public class DatabaseException extends Exception{

    private int errorCode;

    public DatabaseException(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
