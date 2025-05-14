package com.impact.core.module.currency.enun;

/**
 * Enumeration for different currency types.
 * This enum represents the supported currencies used within the system.
 * It includes currency types such as the colon (CURRENCY_COLON) and dollar (CURRENCY_DOLLAR).
 */
public enum ECurrency {
    CURRENCY_COLON,
    CURRENCY_DOLLAR;

    /**
     * Converts the enum constant to a string, removing the "CURRENCY_" prefix.
     *
     * @return the name of the currency without the "CURRENCY_" prefix.
     */@Override
    public String toString() {
        return this.name().replaceFirst("CURRENCY_", "");
    }
}
