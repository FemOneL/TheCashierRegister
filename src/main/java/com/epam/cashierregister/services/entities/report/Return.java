package com.epam.cashierregister.services.entities.report;

import java.math.BigDecimal;

public class Return {
    private int id;
    private int numberOfReturningGoods;
    private BigDecimal returnedSum;

    public Return() {
        this.numberOfReturningGoods = 0;
        this.returnedSum = new BigDecimal(0);
    }

    public Return(int numberOfReturningGoods, BigDecimal returnedSum) {
        this.numberOfReturningGoods = numberOfReturningGoods;
        this.returnedSum = returnedSum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfReturningGoods() {
        return numberOfReturningGoods;
    }

    public void setNumberOfReturningGoods(int numberOfReturningGoods) {
        this.numberOfReturningGoods = numberOfReturningGoods;
    }

    public BigDecimal getReturnedSum() {
        return returnedSum;
    }

    public void setReturnedSum(BigDecimal returnedSum) {
        this.returnedSum = returnedSum;
    }
}
