package com.impact.core.module.assetStatus.enun;

public enum EAssetStatus {
    ASSET_STATUS_AVAILABLE,
    ASSET_STATUS_IN_MAINTENANCE,
    ASSET_STATUS_LOANED,
    ASSET_STATUS_OUT_OF_SERVICE;

    @Override
    public String toString() {
        return name().replace("ASSET_STATUS_", "");
    }
}
