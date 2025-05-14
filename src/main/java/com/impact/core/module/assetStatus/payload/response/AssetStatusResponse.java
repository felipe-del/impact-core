package com.impact.core.module.assetStatus.payload.response;

import com.impact.core.module.assetStatus.enun.EAssetStatus;
import lombok.*;

/**
 * Data Transfer Object (DTO) representing the response structure for an AssetStatus.
 * This class is used to transfer information about asset statuses from the backend to the client.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetStatusResponse {

    /**
     * The unique identifier of the asset status.
     */
    private Integer id;

    /**
     * The name of the asset status, representing its current state.
     * The name is typically one of the values defined in the {@link EAssetStatus} enum.
     */
    private String name;

    /**
     * A detailed description of the asset status.
     */
    private String description;
}
