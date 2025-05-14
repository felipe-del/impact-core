package com.impact.core.module.spaceStatus.enun;

/**
 * Enum representing the different possible states of a space in the system.
 * <p>
 * This enum defines the various statuses that a space can have, such as whether it is available, loaned, in maintenance, out of service, or earmarked.
 */
public enum ESpaceStatus {
    SPACE_STATUS_AVAILABLE,
    SPACE_STATUS_LOANED,
    SPACE_STATUS_IN_MAINTENANCE,
    SPACE_STATUS_OUT_OF_SERVICE,
    SPACE_STATUS_EARRING;


    /**
     * Overrides the default {@link Enum#toString} method to return a more user-friendly version of the enum name.
     * <p>
     * This method removes the "SPACE_STATUS_" prefix and returns the status name in a more readable format.
     *
     * @return a string representing the status without the "SPACE_STATUS_" prefix.
     */
    @Override
    public String toString() {
        return name().replace("SPACE_STATUS_", ""); // Removes the "SPACE_STATUS_" prefix for cleaner output
    }

}
