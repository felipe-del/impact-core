package com.impact.core.module.assetStatus.enun;

/**
 * Enum representing the various statuses an asset can have in the system.
 * Each value corresponds to a different state an asset can be in.
 */
public enum EAssetStatus {
    ASSET_STATUS_AVAILABLE,
    ASSET_STATUS_IN_MAINTENANCE,
    ASSET_STATUS_LOANED,
    ASSET_STATUS_OUT_OF_SERVICE,
    ASSET_STATUS_EARRING;

    /**
     * Overrides the default toString method to return a cleaner version of the status name,
     * removing the "ASSET_STATUS_" prefix.
     *
     * @return A string representing the status without the "ASSET_STATUS_" prefix.
     */
    @Override
    public String toString() {
        return name().replace("ASSET_STATUS_", "");
    }
}
