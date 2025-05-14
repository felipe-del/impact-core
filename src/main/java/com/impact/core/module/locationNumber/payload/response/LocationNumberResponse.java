package com.impact.core.module.locationNumber.payload.response;

import lombok.*;

/**
 * Represents the response payload for a location number.
 * <p>
 * This class is used to send the data related to a location number, which includes the location type name
 * and the location number itself.
 * </p>
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationNumberResponse {

    /**
     * The unique identifier for the location number.
     */
    int id;

    /**
     * The name of the location type associated with the location number.
     */
    String locationTypeName;

    /**
     * The location number.
     */
    int locationNumber;
}
