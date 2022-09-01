package com.epam.cashierregister.services.exeptions;

import com.epam.cashierregister.services.consts.Errors;

/**
 * Exception which throws after try deleting goods which already exist in check
 */
public class GoodsExistInCheckException extends Exception {

    public GoodsExistInCheckException() {
        super(Errors.GOODS_EXIST.name());
    }
}
