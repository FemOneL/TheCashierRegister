package com.epam.cashierregister.services.entities.report;

import com.epam.cashierregister.services.entities.Entity;

import java.math.BigDecimal;

public class Selling implements Entity {
    private int id;
    private int numberOfSellingChecks;
    private BigDecimal sellingSum;

    public Selling() {
        this.numberOfSellingChecks = 0;
        sellingSum = new BigDecimal(0);
    }

    public Selling(int numberOfSellingChecks, BigDecimal sellingSum) {
        this.numberOfSellingChecks = numberOfSellingChecks;
        this.sellingSum = sellingSum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfSellingChecks() {
        return numberOfSellingChecks;
    }

    public void setNumberOfSellingChecks(int numberOfSellingChecks) {
        this.numberOfSellingChecks = numberOfSellingChecks;
    }

    public BigDecimal getSellingSum() {
        return sellingSum;
    }

    public void setSellingSum(BigDecimal sellingSum) {
        this.sellingSum = sellingSum;
    }
}
