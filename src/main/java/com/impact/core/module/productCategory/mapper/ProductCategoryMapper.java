package com.impact.core.module.productCategory.mapper;

import com.impact.core.module.productCategory.entity.ProductCategory;
import com.impact.core.module.productCategory.payload.request.ProductCategoryRequest;
import com.impact.core.module.productCategory.payload.response.ProductCategoryResponse;
import com.impact.core.module.productCategory.service.CategoryTypeService;
import com.impact.core.module.productCategory.service.UnitOfMeasurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductCategoryMapper {

    public final CategoryTypeService categoryTypeService;
    public final CategoryTypeMapper categoryTypeMapper;
    public final UnitOfMeasurementService unitOfMeasurementService;
    public final UnitOfMeasurementMapper unitOfMeasurementMapper;

    public ProductCategory toEntity(ProductCategoryRequest productCategoryRequest) {
        return ProductCategory.builder()
                .name(productCategoryRequest.getName())
                .minimumQuantity(productCategoryRequest.getMinimumQuantity())
                .categoryType(categoryTypeService.findById(productCategoryRequest.getCategoryTypeId()))
                .unitOfMeasurement(unitOfMeasurementService.findById(productCategoryRequest.getUnitOfMeasurementId()))
                .build();
    }

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
