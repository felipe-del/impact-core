package com.impact.core.module.assetRequest.payload.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for receiving asset request data from the frontend.
 * <p>
 * This DTO is used when creating a new asset request, containing information about the asset,
 * the reason for the request, and the expiration date of the request. It includes validation annotations
 * to ensure the integrity of the data provided by the user.
 * </p>
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssetRequestDTORequest {

    /**
     * The ID of the asset being requested.
     * <p>
     * This field is required and must be a positive integer.
     * </p>
     */
    @NotNull(message = "El id del activo es requerido")
    @Positive(message = "El id del activo debe ser un número positivo")
    private int assetId;

    /**
     * The reason for the asset request.
     * <p>
     * This field is required and cannot be blank.
     * </p>
     */
    @NotBlank(message = "La razón es requerida")
    private String reason;

    /**
     * The expiration date of the asset request.
     * <p>
     * This field is required and must be a future date.
     * </p>
     */
    @NotNull(message = "La fecha de expiración de petiticion es requerida")
    @Future(message = "La fecha de expiración de la petición debe ser futura")
    private LocalDate expirationDate;
}
