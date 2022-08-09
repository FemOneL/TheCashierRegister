package com.epam.cashierregister.services.entities.employee;

public enum Role {
    CASHIER("cashier"),
    SENIOR_CASHIER("seniorCashier"),
    COMMODITY_EXPERT("commodityExpert");

    private String roleForTranslate;

    Role(String roleForTranslate) {
        this.roleForTranslate = roleForTranslate;
    }

    public String getRoleForTranslate() {
        return roleForTranslate;
    }

}
