package com.impact.core.module.productCategory.payload.response;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnitOfMeasurementResponse {
    private int id;
    private String name;
    private String abbreviation;
}
