package com.impact.core.module.assetSubcategory.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetCategoryRequest {
    @NotBlank(message = "El nombre es requerido")
    String name;
}
