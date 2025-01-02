package com.impact.core.module.productStatus.enun;

public enum EProductStatus {
    PRODUCT_STATUS_AVAILABLE,
    PRODUCT_STATUS_EARRING,
    PRODUCT_STATUS_LOANED;

    @Override
    public String toString() {
        return name().replace("PRODUCT_STATUS_", "");
    }
}
