package com.impact.core.module.locationNumber.payload.response;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationTypeResponse {
    int id;
    String typeName;
}
