package com.epam.cashierregister.services.entities.employee;

import com.epam.cashierregister.services.entities.Entity;

public enum Role implements Entity {
    CASHIER("cashier"),
    SENIOR_CASHIER("seniorCashier"),
    COMMODITY_EXPERT("commodityExpert"),
    ADMIN("admin"),
    BANNED("banned");

    private final String roleForTranslate;

    Role(String roleForTranslate) {
        this.roleForTranslate = roleForTranslate;
    }

    public String getRoleForTranslate() {
        return roleForTranslate;
    }

}
