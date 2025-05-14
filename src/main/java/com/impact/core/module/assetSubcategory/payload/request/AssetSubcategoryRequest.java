package com.impact.core.module.assetSubcategory.payload.request;

import com.impact.core.module.assetSubcategory.entity.AssetCategory;
import com.impact.core.module.assetSubcategory.entity.AssetSubcategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

/**
 * Data Transfer Object (DTO) class for receiving data to create or update an {@link AssetSubcategory}.
 * <p>
 * This class captures the necessary data to create or update an {@link AssetSubcategory}, with validation
 * constraints to ensure that the required fields are provided and that the {@code assetCategoryId} is a positive integer.
 * </p>
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetSubcategoryRequest {
    /**
     * The name of the {@link AssetSubcategory}.
     */
    @NotBlank(message = "El nombre es requerido")
    String name;

    /**
     * The description of the {@link AssetSubcategory}.
     */
    @NotBlank(message = "La descripción es requerida")
    String description;

    /**
     * The ID of the associated {@link AssetCategory}.
     */
    @Positive(message = "La categoría de activo debe ser un número positivo")
    int assetCategoryId;

}
