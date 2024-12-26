package com.impact.core.module.currency.enun;

public enum ECurrency {
    CURRENCY_COLON,
    CURRENCY_DOLLAR;

    @Override
    public String toString() {
        return this.name().replaceFirst("CURRENCY_", "");
    }
}
