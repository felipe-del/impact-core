package com.impact.core.module.assetModel.mapper;

import com.impact.core.module.assetModel.entity.AssetModel;
import com.impact.core.module.assetModel.payload.request.AssetModelRequest;
import com.impact.core.module.assetModel.payload.response.AssetModelResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between {@link AssetModel} entities and {@link AssetModelRequest} or {@link AssetModelResponse} DTOs.
 * <p>
 * This class provides methods to map data from the request and response payloads to the corresponding entity and vice versa.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class AssetModelMapper {

    /**
     * Convert a {@link AssetModelRequest} to an {@link AssetModel} entity.
     * <p>
     * This method maps the model name from the request DTO to the entity and creates a new {@link AssetModel}.
     * </p>
     *
     * @param assetModelRequest The request payload containing the model name.
     * @return {@link AssetModel} entity with the model name from the request.
     */
    public AssetModel toEntity(AssetModelRequest assetModelRequest) {
        return AssetModel.builder()
                .modelName(assetModelRequest.getModelName())
                .build();
    }

    /**
     * Convert an {@link AssetModel} entity to an {@link AssetModelResponse} DTO.
     * <p>
     * This method maps the entity's id and model name to the response DTO to return to the client.
     * </p>
     *
     * @param assetModel The {@link AssetModel} entity to convert.
     * @return {@link AssetModelResponse} DTO containing the id and model name.
     */
    public AssetModelResponse toDTO(AssetModel assetModel) {
        return AssetModelResponse.builder()
                .id(assetModel.getId())
                .modelName(assetModel.getModelName())
                .build();
    }
}
