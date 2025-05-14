package com.impact.core.module.request.payload;

import com.impact.core.module.resource_request_status.entity.ResourceRequestStatus;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) used to send request data to the frontend.
 * <p>
 * This DTO includes all relevant information about the request
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTOResponse {
    /**
     * The unique identifier for the request.
     */
    private Integer id;

    /**
     * The user who made the request.
     */
    private String user;

    /**
     * The date when the request was made.
     */
    private LocalDateTime requestDate;

    /**
     * The due date by which the request should be fulfilled.
     */
    private LocalDate dueDate;

    /**
     * The start time for the request (if applicable).
     */
    private Instant startTime;

    /**
     * The end time for the request (if applicable).
     */
    private Instant endTime;

    /**
     * The item being requested, such as an asset, product, or space.
     */
    private String requestedItem;

    /**
     * The type of request (e.g., resource type).
     */
    private String type;

    /**
     * Additional details or description of the request.
     */
    private String detail;

    /**
     * The current status of the request.
     * <p>
     * This field represents the current state of the request, such as "Pending", "Approved", or "Denied".
     */
    private String status;

}
