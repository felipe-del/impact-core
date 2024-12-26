package com.impact.core.module.assetModel.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetModelRequest {
    @NotBlank(message = "El nombre del modelo del activo es requerido")
    String modelName;
}
