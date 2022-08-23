package com.epam.cashierregister.services.entities.employee;

public enum Role {
    CASHIER("cashier"),
    SENIOR_CASHIER("seniorCashier"),
    COMMODITY_EXPERT("commodityExpert"),
    ADMIN("admin");

    private final String roleForTranslate;

    Role(String roleForTranslate) {
        this.roleForTranslate = roleForTranslate;
    }

    public String getRoleForTranslate() {
        return roleForTranslate;
    }

}
