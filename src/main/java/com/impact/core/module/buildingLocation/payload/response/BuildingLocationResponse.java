package com.impact.core.module.buildingLocation.payload.response;

import com.impact.core.module.buildingLocation.payload.request.BuildingRequest;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildingLocationResponse {
    int id;
    BuildingResponse building;
    String floor;
}
