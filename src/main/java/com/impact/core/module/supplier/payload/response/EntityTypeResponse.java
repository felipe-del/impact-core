package com.impact.core.module.supplier.payload.response;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntityTypeResponse {
    private int id;
    private String typeName;
}
