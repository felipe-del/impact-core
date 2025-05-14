package com.impact.core.module.assetStatus.mapper;

import com.impact.core.module.assetStatus.entity.AssetStatus;
import com.impact.core.module.assetStatus.payload.response.AssetStatusResponse;
import org.springframework.stereotype.Component;

/**
 * Mapper class responsible for converting {@link AssetStatus} entities to {@link AssetStatusResponse} DTOs.
 * This class is used to map entity data to a format that can be returned to the client.
 */
@Component
public class AssetStatusMapper {

    /**
     * Converts an {@link AssetStatus} entity to an {@link AssetStatusResponse} DTO.
     *
     * @param assetStatus The {@link AssetStatus} entity to be converted.
     * @return An {@link AssetStatusResponse} DTO containing the data from the provided {@link AssetStatus}.
     */
    public AssetStatusResponse toDTO(AssetStatus assetStatus) {
        return AssetStatusResponse.builder()
                .id(assetStatus.getId())
                .name(assetStatus.getName().toString())
                .description(assetStatus.getDescription())
                .build();
    }
}
