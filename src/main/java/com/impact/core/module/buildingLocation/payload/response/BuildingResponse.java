package com.impact.core.module.buildingLocation.payload.response;

import lombok.*;

/**
 * Represents the response Data Transfer Object (DTO) for a building.
 * <p>
 * This class is used to convey basic information about a building,
 * such as its unique identifier and name.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildingResponse {

    /**
     * The unique identifier for the building.
     */
    int id;

    /**
     * The name of the building.
     */
    String name;
}
