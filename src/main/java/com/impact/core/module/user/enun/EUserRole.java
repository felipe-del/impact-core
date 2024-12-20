package com.impact.core.module.user.enun;

public enum EUserRole {
    ROLE_ADMINISTRATOR,
    ROLE_MANAGER,
    ROLE_TEACHER;

    @Override
    public String toString() {
        return this.name().replaceFirst("ROLE_", "");
    }
}
