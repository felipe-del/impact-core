package com.impact.core.module.request.payload;

import com.impact.core.module.resource_request_status.entity.ResourceRequestStatus;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO used to send request data to the frontend.
 * Includes all relevant information about the request,
 * including its current status (e.g., "Pending", "Approved", "Rejected").
 */

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTOResponse {
    private Integer id;
    private String user;
    private LocalDateTime requestDate;
    private LocalDate dueDate;
    private Instant startTime;
    private Instant endTime;
    private String requestedItem;
    private String type;
    private String detail;
    private String status; // "Pending", "Approve", "Denied"

}
