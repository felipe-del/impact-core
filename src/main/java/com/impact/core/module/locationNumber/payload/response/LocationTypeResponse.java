package com.impact.core.module.locationNumber.payload.response;

import lombok.*;

/**
 * Represents the response payload for a location type.
 * <p>
 * This class is used to send the data related to a location type, including its unique identifier and name.
 * </p>
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationTypeResponse {

    /**
     * The unique identifier for the location type.
     */
    int id;

    /**
     * The name of the location type.
     */
    String typeName;
}
