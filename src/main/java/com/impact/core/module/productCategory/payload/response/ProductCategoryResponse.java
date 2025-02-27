package com.impact.core.module.productCategory.payload.response;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryResponse {
    private int id;
    private String name;
    private int minimumQuantity;
    private CategoryTypeResponse categoryType;
    private UnitOfMeasurementResponse unitOfMeasurement;
}
