package com.impact.core.module.spaceStatus.enun;

public enum ESpaceStatus {
    SPACE_STATUS_AVAILABLE,
    SPACE_STATUS_LOANED,
    SPACE_STATUS_IN_MAINTENANCE,
    SPACE_STATUS_OUT_OF_SERVICE;

    @Override
    public String toString() {
        return name().replace("SPACE_STATUS_", "");
    }

}
