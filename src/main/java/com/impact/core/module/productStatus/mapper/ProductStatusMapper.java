package com.impact.core.module.productStatus.mapper;

import com.impact.core.module.productStatus.entity.ProductStatus;
import com.impact.core.module.productStatus.payload.response.ProductStatusResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductStatusMapper {
    public ProductStatusResponse toDTO(ProductStatus productStatus) {
        return ProductStatusResponse.builder()
                .id(productStatus.getId())
                .name(productStatus.getName().toString())
                .description(productStatus.getDescription())
                .build();
    }
}
