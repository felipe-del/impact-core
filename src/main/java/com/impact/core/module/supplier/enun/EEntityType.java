package com.impact.core.module.supplier.enun;

public enum EEntityType {
    TYPE_PHYSICAL,
    TYPE_LEGAL;

    @Override
    public String toString() {
        return this.name().replaceFirst("TYPE_", "");
    }
}
