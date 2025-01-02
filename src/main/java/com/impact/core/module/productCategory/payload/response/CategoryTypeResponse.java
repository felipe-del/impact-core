package com.impact.core.module.productCategory.payload.response;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryTypeResponse {
    private int id;
    private String name;
    private String description;
}
