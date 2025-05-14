package com.impact.core.module.spaceRequest_Reservation.payload.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.Instant;

/**
 * Request DTO class used for creating or updating space requests and space reservations.
 * This DTO combines information needed for both the space request and reservation processes.
 * <p>
 * It includes validation annotations to ensure that the input values are appropriate, such as
 * validating that the number of people and times are positive, future dates are used,
 * and mandatory fields are not left empty.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpaceRndRRequest {

    /**
     * The ID of the space being requested or reserved.
     * Must be a positive integer.
     */
    @Positive(message = "El id debe de tener un valor positivo.")
    private Integer spaceId;      // Information needed for both

    /**
     * The number of people for the event.
     * Must be a positive integer and cannot exceed 100.
     */
    @Positive(message = "El número de personas debe de ser un valor positivo.")
    @Max(value = 100 , message = "El número de personas no puede exceder 100.")
    private Integer numPeople;    // Information needed for SpaceRequest

    /**
     * The description of the event for which the space is being requested.
     * Cannot be blank.
     */
    @NotBlank(message = "La descripción del evento es obligatoria.")
    private String eventDesc;     // Information needed for SpaceRequest

    /**
     * The observation or additional notes for the event.
     * Cannot be null.
     */
    @NotNull(message = "La observación del evento es obligatoria.")
    private String eventObs;      // Information needed for SpaceRequest

    /**
     * Flag indicating whether equipment is required for the event.
     * Cannot be null.
     */
    @NotNull(message = "El flag de uso de equipo es obligatorio.")
    private Boolean useEquipment; // Information needed for SpaceRequest

    /**
     * The start time for the space reservation.
     * Must be a future date.
     */
    @Future(message = "La fecha de inicio debe ser en el futuro.")
    private Instant startTime;  // Information needed for SpaceReservation

    /**
     * The end time for the space reservation.
     * Must be a future date.
     */
    @Future(message = "La fecha de inicio debe ser en el futuro.")
    private Instant endTime;    // Information needed for SpaceReservation
}
