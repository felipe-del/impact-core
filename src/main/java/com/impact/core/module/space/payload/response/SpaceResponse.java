package com.impact.core.module.space.payload.response;

import com.impact.core.module.buildingLocation.payload.response.BuildingLocationResponse;
import com.impact.core.module.spaceStatus.payload.response.SpaceStatusResponse;
import lombok.*;

/**
 * Response Data Transfer Object (DTO) representing a {@link com.impact.core.module.space.entity.Space}.
 * <p>
 * This class encapsulates the space's information returned to the client after read, create,
 * update, or delete operations.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpaceResponse {

    /**
     * Unique identifier of the space.
     */
    private int id;

    /**
     * Name of the space.
     */
    private String name;

    /**
     * Numeric code assigned to the space.
     */
    private int spaceCode;

    /**
     * Response Data Transfer Object (DTO) representing the building location where the space is situated.
     * <p>
     * Refers to {@link BuildingLocationResponse}.
     */
    private BuildingLocationResponse buildingLocationResponse;

    /**
     * Maximum number of people allowed in the space.
     */
    private int maxPeople;

    /**
     * Opening time of the space in {@code HH:mm} format.
     */
    private String openTime;

    /**
     * Closing time of the space in {@code HH:mm} format.
     */
    private String closeTime;

    /**
     * Response Data Transfer Object (DTO) representing the current status of the space.
     * <p>
     * Refers to {@link SpaceStatusResponse}.
     */
    private SpaceStatusResponse spaceStatus;
}
