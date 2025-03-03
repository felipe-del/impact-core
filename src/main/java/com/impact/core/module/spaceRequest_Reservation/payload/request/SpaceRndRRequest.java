package com.impact.core.module.spaceRequest_Reservation.payload.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpaceRndRRequest {

    @NotBlank(message = "El id del espacio es requerido.")
    @Positive(message = "El id debe de tener un valor positivo.")
    private Integer spaceId;      // Information needed for both

    @NotBlank(message = "El número de personas es obligatorio.")
    @Positive(message = "El número de personas debe de ser un valor positivo.")
    @Max(value = 100 , message = "El número de personas no puede exceder 100.")
    private Integer numPeople;    // Information needed for SpaceRequest

    @NotBlank(message = "La descripción del evento es obligatoria.")
    private String eventDesc;     // Information needed for SpaceRequest

    private String eventObs;      // Information needed for SpaceRequest

    @NotBlank(message = "La descripción del evento es obligatoria.")
    private Integer statusId;     // Information needed for SpaceRequest

    private Boolean useEquipment; // Information needed for SpaceRequest

    @NotBlank(message = "La descripción del evento es obligatoria.")
    @Future(message = "La fecha de inicio debe ser en el futuro.")
    private Instant startTime;  // Information needed for SpaceReservation

    @NotBlank(message = "La descripción del evento es obligatoria.")
    @Future(message = "La fecha de inicio debe ser en el futuro.")
    private Instant endTime;    // Information needed for SpaceReservation
}
