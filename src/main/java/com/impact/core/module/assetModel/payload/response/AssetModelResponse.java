package com.impact.core.module.assetModel.payload.response;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetModelResponse {
    int id;
    String modelName;
}
