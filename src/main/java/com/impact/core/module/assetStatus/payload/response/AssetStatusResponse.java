package com.impact.core.module.assetStatus.payload.response;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetStatusResponse {
    private Integer id;
    private String name;
    private String description;
}
