package com.epam.cashierregister.services.exeptions;

/**
 * Exception which throws after try deleting goods which already exist in check
 */
public class GoodsExistInCheckException extends Exception {

    public GoodsExistInCheckException() {
        super("This goods exist in check you can't delete it");
    }
}
