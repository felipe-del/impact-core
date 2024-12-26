package com.impact.core.module.assetSubcategory.mapper;

import com.impact.core.module.assetSubcategory.entity.AssetCategory;
import com.impact.core.module.assetSubcategory.payload.request.AssetCategoryRequest;
import com.impact.core.module.assetSubcategory.payload.response.AssetCategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssetCategoryMapper {
    public AssetCategoryResponse toDTO(AssetCategory assetCategory) {
        return AssetCategoryResponse.builder()
                .id(assetCategory.getId())
                .name(assetCategory.getName())
                .build();
    }
    public AssetCategory toEntity(AssetCategoryRequest assetCategoryRequest) {
        return AssetCategory.builder()
                .name(assetCategoryRequest.getName())
                .build();
    }
}
