package com.impact.core.module.productCategory.mapper;

import com.impact.core.module.productCategory.entity.CategoryType;
import com.impact.core.module.productCategory.payload.request.CategoryTypeRequest;
import com.impact.core.module.productCategory.payload.response.CategoryTypeResponse;
import org.springframework.stereotype.Component;

@Component
public class CategoryTypeMapper {

    public CategoryType toEntity(CategoryTypeRequest categoryTypeRequest) {
        return CategoryType.builder()
                .name(categoryTypeRequest.getName())
                .description(categoryTypeRequest.getDescription())
                .build();
    }

    public CategoryTypeResponse toDTO(CategoryType categoryType) {
        return CategoryTypeResponse.builder()
                .id(categoryType.getId())
                .name(categoryType.getName())
                .description(categoryType.getDescription())
                .build();
    }

}
