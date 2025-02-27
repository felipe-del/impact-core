package com.impact.core.module.assetSubcategory.mapper;

import com.impact.core.module.assetSubcategory.entity.AssetCategory;
import com.impact.core.module.assetSubcategory.entity.AssetSubcategory;
import com.impact.core.module.assetSubcategory.payload.request.AssetCategoryRequest;
import com.impact.core.module.assetSubcategory.payload.request.AssetSubcategoryRequest;
import com.impact.core.module.assetSubcategory.payload.response.AssetSubcategoryResponse;
import com.impact.core.module.assetSubcategory.service.AssetCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssetSubcategoryMapper {
    public final AssetCategoryService assetCategoryService;

    public AssetSubcategory toEntity(AssetSubcategoryRequest assetSubcategoryRequest) {
        AssetCategory assetCategory = assetCategoryService.findById(assetSubcategoryRequest.getAssetCategoryId());
        return AssetSubcategory.builder()
                .name(assetSubcategoryRequest.getName())
                .description(assetSubcategoryRequest.getDescription())
                .category(assetCategory)
                .build();
    }

    public AssetSubcategoryResponse toDTO(AssetSubcategory assetSubcategory) {
        return AssetSubcategoryResponse.builder()
                .id(assetSubcategory.getId())
                .name(assetSubcategory.getName())
                .description(assetSubcategory.getDescription())
                .assetCategoryName(assetSubcategory.getCategory().getName())
                .build();
    }
}
