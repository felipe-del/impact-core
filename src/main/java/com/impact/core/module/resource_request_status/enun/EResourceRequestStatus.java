package com.impact.core.module.resource_request_status.enun;

/**
 * Enum representing the possible statuses of a resource request.
 * <p>
 * This enum defines the various states that a resource request can be in, such as accepted, returned,
 * or canceled. Each enum constant represents a specific state in the lifecycle of a resource request.
 */
public enum EResourceRequestStatus {
    RESOURCE_REQUEST_STATUS_EARRING,
    RESOURCE_REQUEST_STATUS_ACCEPTED,
    RESOURCE_REQUEST_STATUS_RETURNED,
    RESOURCE_REQUEST_STATUS_CANCELED,
    RESOURCE_REQUEST_STATUS_RENEWAL,
    RESOURCE_REQUEST_STATUS_WAITING_ON_RENEWAL;

    /**
     * Converts the enum constant to a human-readable string.
     * <p>
     * This method removes the "RESOURCE_REQUEST_STATUS_" prefix from the enum constant name,
     * returning only the meaningful part of the status name (e.g., "EARRING" instead of "RESOURCE_REQUEST_STATUS_EARRING").
     *
     * @return the string representation of the enum constant without the "RESOURCE_REQUEST_STATUS_" prefix.
     */
    @Override
    public String toString() {
        return this.name().replaceFirst("RESOURCE_REQUEST_STATUS_", "");
    }
}
