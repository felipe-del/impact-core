package com.impact.core.module.productStatus.payload.response;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductStatusResponse {
    private Integer id;
    private String name;
    private String description;
}
