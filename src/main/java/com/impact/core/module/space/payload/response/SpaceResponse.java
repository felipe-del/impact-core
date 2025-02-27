package com.impact.core.module.space.payload.response;

import com.impact.core.module.buildingLocation.payload.response.BuildingLocationResponse;
import com.impact.core.module.spaceStatus.payload.response.SpaceStatusResponse;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpaceResponse {
    private int id;
    private String name;
    private int spaceCode;
    private BuildingLocationResponse buildingLocationResponse;
    private int maxPeople;
    private String openTime;
    private String closeTime;
    private SpaceStatusResponse spaceStatus;
}
