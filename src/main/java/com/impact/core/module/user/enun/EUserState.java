package com.impact.core.module.user.enun;

public enum EUserState {
    STATE_ACTIVE,
    STATE_INACTIVE,
    STATE_SUSPENDED;

    @Override
    public String toString() {
        return this.name().replaceFirst("STATE_", "");
    }
}
