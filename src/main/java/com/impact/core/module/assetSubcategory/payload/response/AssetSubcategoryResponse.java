package com.impact.core.module.assetSubcategory.payload.response;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetSubcategoryResponse {
    int id;
    String name;
    String description;
    String assetCategoryName;
}
