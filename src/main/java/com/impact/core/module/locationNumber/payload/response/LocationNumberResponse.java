package com.impact.core.module.locationNumber.payload.response;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationNumberResponse {
    int id;
    String locationTypeName;
    int locationNumber;
}
