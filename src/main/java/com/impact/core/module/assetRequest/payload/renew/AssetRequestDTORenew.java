package com.impact.core.module.assetRequest.payload.renew;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for renewing an asset request.
 * <p>
 * This DTO is used to represent the data required to renew an asset request,
 * including the asset's ID, the reason for the renewal, the new expiration date,
 * and the creation date of the original request. It ensures that only valid
 * data is submitted when renewing an asset request.
 * </p>
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssetRequestDTORenew {

    /**
     * The unique identifier of the asset being renewed.
     * <p>
     * This field represents the ID of the asset, ensuring that the correct asset
     * is identified for the renewal request. It must be a positive number.
     * </p>
     */
    @NotNull(message = "El id del activo es requerido")
    @Positive(message = "El id del activo debe ser un número positivo")
    private int assetId;

    /**
     * The reason for the asset request renewal.
     * <p>
     * This field contains the reason that justifies why the asset request is being renewed.
     * It is required and cannot be blank.
     * </p>
     */
    @NotBlank(message = "La razón es requerida")
    private String reason;

    /**
     * The new expiration date for the asset request renewal.
     * <p>
     * This field represents the new expiration date of the asset request. The expiration
     * date must be in the future to ensure that the renewal request is valid.
     * </p>
     */
    @NotNull(message = "La fecha de expiración de petiticion es requerida")
    @Future(message = "La fecha de expiración de la petición debe ser futura")
    private LocalDate expirationDate;

    /**
     * The creation date of the original asset request.
     * <p>
     * This field represents the date when the original asset request was created.
     * It is required to ensure that the renewal process maintains proper tracking of the request's lifecycle.
     * </p>
     */
    @NotNull(message = "La fecha de creación es requerida")
    private LocalDate createdAt;
}
