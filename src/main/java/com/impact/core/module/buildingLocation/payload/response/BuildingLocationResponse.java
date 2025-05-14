package com.impact.core.module.buildingLocation.payload.response;

import lombok.*;

/**
 * Represents the response Data Transfer Object (DTO) for a building location.
 * <p>
 * This class is typically used to return building location information,
 * including the building itself, its identifier, and the floor.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildingLocationResponse {

    /**
     * The unique identifier for the building location.
     */
    int id;

    /**
     * The building associated with this location.
     */
    BuildingResponse building;

    /**
     * The floor on which the building location resides.
     */
    String floor;
}
