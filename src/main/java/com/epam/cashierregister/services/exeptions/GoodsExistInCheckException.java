package com.epam.cashierregister.services.exeptions;

public class GoodsExistInCheckException extends Exception {

    public GoodsExistInCheckException() {
        super("This goods exist in check you can't delete it");
    }
}
