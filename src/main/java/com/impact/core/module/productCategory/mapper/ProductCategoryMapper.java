package com.impact.core.module.productCategory.mapper;

import com.impact.core.module.productCategory.entity.ProductCategory;
import com.impact.core.module.productCategory.payload.request.ProductCategoryRequest;
import com.impact.core.module.productCategory.payload.response.ProductCategoryResponse;
import com.impact.core.module.productCategory.service.CategoryTypeService;
import com.impact.core.module.productCategory.service.UnitOfMeasurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper class that converts {@link ProductCategory} entities to {@link ProductCategoryResponse} DTOs and
 * {@link ProductCategoryRequest} DTOs to {@link ProductCategory} entities.
 */
@Component
@RequiredArgsConstructor
public class ProductCategoryMapper {

    public final CategoryTypeService categoryTypeService;
    public final CategoryTypeMapper categoryTypeMapper;
    public final UnitOfMeasurementService unitOfMeasurementService;
    public final UnitOfMeasurementMapper unitOfMeasurementMapper;

    /**
     * Converts a {@link ProductCategoryRequest} Data Transfer Object (DTO) to a {@link ProductCategory} entity.
     *
     * @param productCategoryRequest The {@link ProductCategoryRequest} DTO to be converted.
     * @return A {@link ProductCategory} entity containing the data from the provided DTO.
     */
    public ProductCategory toEntity(ProductCategoryRequest productCategoryRequest) {
        return ProductCategory.builder()
                .name(productCategoryRequest.getName())
                .minimumQuantity(productCategoryRequest.getMinimumQuantity())
                .categoryType(categoryTypeService.findById(productCategoryRequest.getCategoryTypeId()))
                .unitOfMeasurement(unitOfMeasurementService.findById(productCategoryRequest.getUnitOfMeasurementId()))
                .build();
    }

    /**
     * Converts a {@link ProductCategory} entity to a {@link ProductCategoryResponse} Data Transfer Object (DTO).
     *
     * @param productCategory The {@link ProductCategory} entity to be converted.
     * @return A {@link ProductCategoryResponse} DTO containing the data of the provided entity.
     */
    public ProductCategoryResponse toDTO(ProductCategory productCategory) {
        return ProductCategoryResponse.builder()
                .id(productCategory.getId())
                .name(productCategory.getName())
                .minimumQuantity(productCategory.getMinimumQuantity())
                .categoryType(categoryTypeMapper.toDTO(productCategory.getCategoryType()))
                .unitOfMeasurement(unitOfMeasurementMapper.toDTO(productCategory.getUnitOfMeasurement()))
                .build();
    }
}
