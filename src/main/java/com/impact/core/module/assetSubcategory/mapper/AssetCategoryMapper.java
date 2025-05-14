package com.impact.core.module.assetSubcategory.mapper;

import com.impact.core.module.assetSubcategory.entity.AssetCategory;
import com.impact.core.module.assetSubcategory.payload.request.AssetCategoryRequest;
import com.impact.core.module.assetSubcategory.payload.response.AssetCategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper class responsible for converting between {@link AssetCategory} entities and their corresponding
 * {@link AssetCategoryRequest} and {@link AssetCategoryResponse} Data Transfer Objects (DTOs).
 * <p>
 * This class provides methods to map {@link AssetCategory} entities to DTOs and vice versa, facilitating
 * the transfer of data between layers of the application (e.g., service layer and controller layer).
 * </p>
 */
@Component
@RequiredArgsConstructor
public class AssetCategoryMapper {

    /**
     * Converts an {@link AssetCategory} entity to an {@link AssetCategoryResponse} DTO.
     *
     * @param assetCategory the {@link AssetCategory} entity to convert.
     * @return an {@link AssetCategoryResponse} DTO representing the {@link AssetCategory} entity.
     */
    public AssetCategoryResponse toDTO(AssetCategory assetCategory) {
        return AssetCategoryResponse.builder()
                .id(assetCategory.getId())
                .name(assetCategory.getName())
                .build();
    }

    /**
     * Converts an {@link AssetCategoryRequest} DTO to an {@link AssetCategory} entity.
     *
     * @param assetCategoryRequest the {@link AssetCategoryRequest} DTO to convert.
     * @return an {@link AssetCategory} entity based on the provided DTO.
     */
    public AssetCategory toEntity(AssetCategoryRequest assetCategoryRequest) {
        return AssetCategory.builder()
                .name(assetCategoryRequest.getName())
                .build();
    }
}
