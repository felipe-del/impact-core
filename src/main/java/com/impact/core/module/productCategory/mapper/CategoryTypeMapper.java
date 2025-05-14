package com.impact.core.module.productCategory.mapper;

import com.impact.core.module.productCategory.entity.CategoryType;
import com.impact.core.module.productCategory.payload.request.CategoryTypeRequest;
import com.impact.core.module.productCategory.payload.response.CategoryTypeResponse;
import org.springframework.stereotype.Component;

/**
 * Mapper class that converts {@link CategoryType} entities to {@link CategoryTypeResponse} DTOs and
 * {@link CategoryTypeRequest} DTOs to {@link CategoryType} entities.
 */
@Component
public class CategoryTypeMapper {

    /**
     * Converts a {@link CategoryTypeRequest} Data Transfer Object (DTO) to a {@link CategoryType} entity.
     *
     * @param categoryTypeRequest The {@link CategoryTypeRequest} DTO to be converted.
     * @return A {@link CategoryType} entity containing the data from the provided DTO.
     */
    public CategoryType toEntity(CategoryTypeRequest categoryTypeRequest) {
        return CategoryType.builder()
                .name(categoryTypeRequest.getName())
                .description(categoryTypeRequest.getDescription())
                .build();
    }

    /**
     * Converts a {@link CategoryType} entity to a {@link CategoryTypeResponse} Data Transfer Object (DTO).
     *
     * @param categoryType The {@link CategoryType} entity to be converted.
     * @return A {@link CategoryTypeResponse} DTO containing the data of the provided entity.
     */
    public CategoryTypeResponse toDTO(CategoryType categoryType) {
        return CategoryTypeResponse.builder()
                .id(categoryType.getId())
                .name(categoryType.getName())
                .description(categoryType.getDescription())
                .build();
    }

}
