package com.impact.core.module.assetSubcategory.mapper;

import com.impact.core.module.assetSubcategory.entity.AssetCategory;
import com.impact.core.module.assetSubcategory.entity.AssetSubcategory;
import com.impact.core.module.assetSubcategory.payload.request.AssetCategoryRequest;
import com.impact.core.module.assetSubcategory.payload.request.AssetSubcategoryRequest;
import com.impact.core.module.assetSubcategory.payload.response.AssetSubcategoryResponse;
import com.impact.core.module.assetSubcategory.service.AssetCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper class responsible for converting between {@link AssetSubcategory} entities and their corresponding
 * {@link AssetSubcategoryRequest} and {@link AssetSubcategoryResponse} Data Transfer Objects (DTOs).
 * <p>
 * This class provides methods to map {@link AssetSubcategory} entities to DTOs and vice versa, facilitating
 * the transfer of data between layers of the application (e.g., service layer and controller layer).
 * It also handles the mapping of the {@link AssetCategory} related to the {@link AssetSubcategory}.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class AssetSubcategoryMapper {
    public final AssetCategoryService assetCategoryService;

    /**
     * Converts an {@link AssetSubcategoryRequest} DTO to an {@link AssetSubcategory} entity.
     * <p>
     * This method retrieves the {@link AssetCategory} based on the {@code assetCategoryId} provided in the DTO
     * and sets it on the new {@link AssetSubcategory} entity.
     * </p>
     *
     * @param assetSubcategoryRequest the {@link AssetSubcategoryRequest} DTO to convert.
     * @return an {@link AssetSubcategory} entity based on the provided DTO and {@link AssetCategory}.
     */
    public AssetSubcategory toEntity(AssetSubcategoryRequest assetSubcategoryRequest) {
        AssetCategory assetCategory = assetCategoryService.findById(assetSubcategoryRequest.getAssetCategoryId());
        return AssetSubcategory.builder()
                .name(assetSubcategoryRequest.getName())
                .description(assetSubcategoryRequest.getDescription())
                .category(assetCategory)
                .build();
    }

    /**
     * Converts an {@link AssetSubcategory} entity to an {@link AssetSubcategoryResponse} DTO.
     *
     * @param assetSubcategory the {@link AssetSubcategory} entity to convert.
     * @return an {@link AssetSubcategoryResponse} DTO representing the {@link AssetSubcategory} entity.
     */
    public AssetSubcategoryResponse toDTO(AssetSubcategory assetSubcategory) {
        return AssetSubcategoryResponse.builder()
                .id(assetSubcategory.getId())
                .name(assetSubcategory.getName())
                .description(assetSubcategory.getDescription())
                .assetCategoryName(assetSubcategory.getCategory().getName())
                .build();
    }
}
