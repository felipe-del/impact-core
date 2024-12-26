package com.impact.core.module.assetSubcategory.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetSubcategoryRequest {
    @NotBlank(message = "El nombre es requerido")
    String name;
    @NotBlank(message = "La descripción es requerida")
    String description;
    @Positive(message = "La categoría de activo debe ser un número positivo")
    int assetCategoryId;

}
