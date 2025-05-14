package com.impact.core.module.assetSubcategory.payload.request;

import com.impact.core.module.assetSubcategory.entity.AssetCategory;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Data Transfer Object (DTO) class for receiving data to create or update an {@link AssetCategory}.
 * <p>
 * This class is used to capture the data for an {@link AssetCategory} from the client side, including validation
 * constraints on the {@code name} field to ensure data integrity.
 * </p>
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetCategoryRequest {
    /**
     * The name of the {@link AssetCategory}.
     */
    @NotBlank(message = "El nombre es requerido")
    String name;
}
