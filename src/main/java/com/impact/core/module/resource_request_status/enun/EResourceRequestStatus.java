package com.impact.core.module.resource_request_status.enun;

public enum EResourceRequestStatus {
    RESOURCE_REQUEST_STATUS_EARRING,
    RESOURCE_REQUEST_STATUS_ACCEPTED,
    RESOURCE_REQUEST_STATUS_RETURNED,
    RESOURCE_REQUEST_STATUS_CANCELED;

    @Override
    public String toString() {
        return this.name().replaceFirst("RESOURCE_REQUEST_STATUS_", "");
    }
}
