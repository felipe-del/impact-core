package com.impact.core.module.assetSubcategory.payload.response;

import com.impact.core.module.assetSubcategory.entity.AssetCategory;
import lombok.*;

/**
 * Data Transfer Object (DTO) class representing the response for an {@link AssetCategory}.
 * <p>
 * This class is used to send the details of an {@link AssetCategory} to the client side, including the
 * {@code id} and {@code name} of the category.
 * </p>
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetCategoryResponse {

    /**
     * The ID of the {@link AssetCategory}.
     */
    int id;

    /**
     * The name of the {@link AssetCategory}.
     */
    String name;
}
