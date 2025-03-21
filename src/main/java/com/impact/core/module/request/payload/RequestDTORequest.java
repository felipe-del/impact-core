package com.impact.core.module.request.payload;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

/**
 * DTO used to receive request data from the frontend.
 * It contains information about the user making the request,
 * the requested item (asset, product, or space), and additional details.
 * The `id` field is optional and only used when updating a request.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTORequest {
    @NotNull(message= "Es necesario un id del request correspondiente")
    private Long id;
    private String user;
    private LocalDate requestDate;
    private LocalDate dueDate;
    private String requestedItem;
    private String type;
    private String detail;
}
