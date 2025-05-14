package com.impact.core.module.brand.mapper;

import com.impact.core.module.brand.entity.Brand;
import com.impact.core.module.brand.payload.request.BrandRequest;
import com.impact.core.module.brand.payload.response.BrandResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between `Brand` entity and its request/response representations.
 * This class provides methods to map between the `Brand` entity, the `BrandRequest` payload,
 * and the `BrandResponse` payload.
 */
@Component
@RequiredArgsConstructor
public class BrandMapper {

    /**
     * Converts a `BrandRequest` object to a `Brand` entity.
     *
     * @param brandRequest the request object containing brand details.
     * @return the mapped `Brand` entity.
     */
    public Brand toEntity(BrandRequest brandRequest) {
        return Brand.builder()
                .name(brandRequest.getName())
                .build();
    }

    /**
     * Converts a `Brand` entity to a `BrandResponse` object.
     *
     * @param brand the `Brand` entity to convert.
     * @return the mapped `BrandResponse` object.
     */
    public BrandResponse toDTO(Brand brand) {
        return BrandResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .build();
    }
}
