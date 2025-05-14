package com.impact.core.module.productStatus.mapper;

import com.impact.core.module.productStatus.entity.ProductStatus;
import com.impact.core.module.productStatus.payload.response.ProductStatusResponse;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting ProductStatus entities to ProductStatusResponse DTOs.
 * <p>
 * This component is responsible for transforming the data between the ProductStatus
 * entity and the ProductStatusResponse DTO, which is used to send data to the frontend.
 * </p>
 */
@Component
public class ProductStatusMapper {

    /**
     * Converts a ProductStatus entity to a ProductStatusResponse DTO.
     * <p>
     * This method takes a ProductStatus entity and converts it into a ProductStatusResponse
     * DTO that includes the necessary data to be sent to the frontend.
     * </p>
     *
     * @param productStatus the ProductStatus entity to convert.
     * @return a ProductStatusResponse DTO containing the entity data.
     */
    public ProductStatusResponse toDTO(ProductStatus productStatus) {
        return ProductStatusResponse.builder()
                .id(productStatus.getId())
                .name(productStatus.getName().toString())
                .description(productStatus.getDescription())
                .build();
    }
}
