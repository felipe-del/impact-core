package com.impact.core.module.assetModel.payload.response;

import lombok.*;

/**
 * Response Data Transfer Object (DTO) for the asset model.
 * <p>
 * This class represents the response structure for an asset model, containing its identifier and model name.
 * </p>
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetModelResponse {

    /**
     * The unique identifier of the asset model.
     */
    int id;

    /**
     * The name of the asset model.
     */
    String modelName;
}
