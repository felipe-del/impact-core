package com.impact.core.module.assetModel.payload.request;

import com.impact.core.module.assetModel.entity.AssetModel;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Data Transfer Object (DTO) for the request payload to create or update an {@link AssetModel}.
 * <p>
 * This class contains the necessary fields to create or modify an asset model in the system.
 * </p>
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetModelRequest {

    /**
     * The name of the asset model.
     * <p>
     * This field is required and cannot be blank. It represents the model name of the asset in the system.
     * </p>
     */
    @NotBlank(message = "El nombre del modelo del activo es requerido")
    String modelName;
}
