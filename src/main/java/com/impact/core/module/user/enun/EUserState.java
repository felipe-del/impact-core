package com.impact.core.module.user.enun;

/**
 * Enum representing the various states a user account can be in within the system.
 */
public enum EUserState {
    STATE_ACTIVE,
    STATE_INACTIVE,
    STATE_SUSPENDED;

    /**
     * Converts the enum name to a more user-friendly format by removing the "STATE_" prefix.
     *
     * @return the user state without the "STATE_" prefix (e.g., "ACTIVE")
     */
    @Override
    public String toString() {
        return this.name().replaceFirst("STATE_", "");
    }
}
