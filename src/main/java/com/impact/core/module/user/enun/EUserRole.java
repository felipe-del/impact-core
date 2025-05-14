package com.impact.core.module.user.enun;

/**
 * Enum representing the different roles a user can have in the system.
 * Each role corresponds to a specific level of access or responsibility.
 */
public enum EUserRole {
    ROLE_ADMINISTRATOR,
    ROLE_MANAGER,
    ROLE_TEACHER;

    /**
     * Converts the enum name to a more user-friendly format by removing the "ROLE_" prefix.
     *
     * @return the role name without the "ROLE_" prefix (e.g., "ADMINISTRATOR")
     */
    @Override
    public String toString() {
        return this.name().replaceFirst("ROLE_", "");
    }
}
