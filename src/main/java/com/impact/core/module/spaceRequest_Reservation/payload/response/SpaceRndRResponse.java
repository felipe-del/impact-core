package com.impact.core.module.spaceRequest_Reservation.payload.response;

import com.impact.core.module.resource_request_status.payload.response.ResourceRequestStatusResponse;
import com.impact.core.module.space.payload.response.SpaceResponse;
import com.impact.core.module.spaceStatus.payload.response.SpaceStatusResponse;
import com.impact.core.module.user.payload.response.UserResponse;
import lombok.*;

import java.time.Instant;

/**
 * Response DTO class for the space request and reservation details.
 * This DTO is used to send the response data after a space request is created, updated, or retrieved.
 * <p>
 * It contains all the necessary information about the space, event details, status, and associated user.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpaceRndRResponse {

    /**
     * The unique identifier of the space request or reservation.
     */
    private Integer id;

    /**
     * The space details, including the space name, description, and related information.
     */
    private SpaceResponse space;

    /**
     * The request and reservation ID.
     */
    private int reqAndResId;

    /**
     * The number of people attending the event.
     */
    private int numPeople;

    /**
     * The description of the event.
     */
    private String eventDesc;

    /**
     * Additional observations or notes about the event.
     */
    private String eventObs;

    /**
     * The status of the space request, such as pending, approved, or rejected.
     */
    private ResourceRequestStatusResponse status;

    /**
     * A flag indicating whether equipment is needed for the event.
     */
    private Boolean useEquipment;

    /**
     * The start time of the space reservation.
     */
    private Instant startTime;

    /**
     * The end time of the space reservation.
     */
    private Instant endTime;

    /**
     * The user who made the space request or reservation.
     */
    private UserResponse user;

    /**
     * The creation timestamp of the space request or reservation.
     */
    private String createdAt;
}
