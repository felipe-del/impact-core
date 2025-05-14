package com.impact.core.module.request.payload;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) used to receive request data from the frontend.
 * The `id` field is required when updating an existing request, but it is optional for new requests.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTORequest {
    /**
     * The unique identifier for the request.
     */
    @NotNull(message= "Es necesario un id del request correspondiente")
    private Long id;

    /**
     * The user who is making the request.
     */
    private String user;

    /**
     * The date when the request was made.
     */
    private LocalDate requestDate;

    /**
     * The due date by which the request should be fulfilled.
     */
    private LocalDate dueDate;

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
}
