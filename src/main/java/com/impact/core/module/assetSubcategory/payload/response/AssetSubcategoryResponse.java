package com.impact.core.module.assetSubcategory.payload.response;

import com.impact.core.module.assetSubcategory.entity.AssetCategory;
import com.impact.core.module.assetSubcategory.entity.AssetSubcategory;
import lombok.*;

/**
 * Data Transfer Object (DTO) class representing the response for an {@link AssetSubcategory}.
 * <p>
 * This class is used to send the details of an {@link AssetSubcategory} to the client side, including the
 * {@code id}, {@code name}, {@code description}, and the name of the associated {@link AssetCategory}.
 * </p>
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetSubcategoryResponse {

    /**
     * The ID of the {@link AssetSubcategory}.
     */
    int id;

    /**
     * The name of the {@link AssetSubcategory}.
     */
    String name;

    /**
     * The description of the {@link AssetSubcategory}.
     */
    String description;

    /**
     * The name of the associated {@link AssetCategory} of the {@link AssetSubcategory}.
     */
    String assetCategoryName;
}
