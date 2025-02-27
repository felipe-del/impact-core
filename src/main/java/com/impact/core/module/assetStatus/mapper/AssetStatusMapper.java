package com.impact.core.module.assetStatus.mapper;

import com.impact.core.module.assetStatus.entity.AssetStatus;
import com.impact.core.module.assetStatus.payload.response.AssetStatusResponse;
import org.springframework.stereotype.Component;

@Component
public class AssetStatusMapper {
    public AssetStatusResponse toDTO(AssetStatus assetStatus) {
        return AssetStatusResponse.builder()
                .id(assetStatus.getId())
                .name(assetStatus.getName().toString())
                .description(assetStatus.getDescription())
                .build();
    }
}
