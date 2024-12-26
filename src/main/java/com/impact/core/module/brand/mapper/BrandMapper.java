package com.impact.core.module.brand.mapper;

import com.impact.core.module.brand.entity.Brand;
import com.impact.core.module.brand.payload.request.BrandRequest;
import com.impact.core.module.brand.payload.response.BrandResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BrandMapper {
    public Brand toEntity(BrandRequest brandRequest) {
        return Brand.builder()
                .name(brandRequest.getName())
                .build();
    }

    public BrandResponse toDTO(Brand brand) {
        return BrandResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .build();
    }
}
