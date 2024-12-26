package com.impact.core.module.assetModel.mapper;

import com.impact.core.module.assetModel.entity.AssetModel;
import com.impact.core.module.assetModel.payload.request.AssetModelRequest;
import com.impact.core.module.assetModel.payload.response.AssetModelResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssetModelMapper {

    public AssetModel toEntity(AssetModelRequest assetModelRequest) {
        return AssetModel.builder()
                .modelName(assetModelRequest.getModelName())
                .build();
    }

    public AssetModelResponse toDTO(AssetModel assetModel) {
        return AssetModelResponse.builder()
                .id(assetModel.getId())
                .modelName(assetModel.getModelName())
                .build();
    }
}
