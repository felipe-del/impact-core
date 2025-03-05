package com.impact.core.module.spaceRequest_Reservation.payload.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpaceRndRRequest {

    @Positive(message = "El id debe de tener un valor positivo.")
    private Integer spaceId;      // Information needed for both

    @Positive(message = "El número de personas debe de ser un valor positivo.")
    @Max(value = 100 , message = "El número de personas no puede exceder 100.")
    private Integer numPeople;    // Information needed for SpaceRequest

    @NotBlank(message = "La descripción del evento es obligatoria.")
    private String eventDesc;     // Information needed for SpaceRequest

    private String eventObs;      // Information needed for SpaceRequest

    @NotNull(message = "El flag de uso de equipo es obligatorio.")
    private Boolean useEquipment; // Information needed for SpaceRequest

    @Future(message = "La fecha de inicio debe ser en el futuro.")
    private Instant startTime;  // Information needed for SpaceReservation

    @Future(message = "La fecha de inicio debe ser en el futuro.")
    private Instant endTime;    // Information needed for SpaceReservation
}
