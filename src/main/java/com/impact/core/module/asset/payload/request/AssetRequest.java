package com.impact.core.module.asset.payload.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Data Transfer Object for creating or updating an Asset.
 * Validations are applied to ensure input consistency and integrity.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssetRequest {

    /**
     * The purchase date of the asset.
     * Must not be null and cannot be a future date.
     */
    @NotNull(message = "La fecha de compra es requerida.")
    @PastOrPresent(message = "La fecha de compra no puede ser en el futuro.")
    private LocalDate purchaseDate;

    /**
     * The monetary value of the asset.
     * Must be positive and not null.
     */
    @NotNull(message = "El valor del activo es requerido.")
    @Positive(message = "El valor del activo debe ser un número positivo.")
    private BigDecimal value;

    /**
     * ID of the responsible user.
     * Must be positive and not null.
     */
    @NotNull(message = "El ID del usuario es requerido.")
    @Positive(message = "El ID del usuario debe ser un número positivo.")
    private int userId;

    /**
     * ID of the supplier.
     * Must be positive and not null.
     */
    @NotNull(message = "El ID del proveedor es requerido.")
    @Positive(message = "El ID del proveedor debe ser un número positivo.")
    private int supplierId;

    /**
     * ID of the subcategory to which the asset belongs.
     * Must be positive and not null.
     */
    @NotNull(message = "El ID de la subcategoría es requerido.")
    @Positive(message = "El ID de la subcategoría debe ser un número positivo.")
    private int subCategoryId;

    /**
     * ID of the brand of the asset.
     * Must be positive and not null.
     */
    @NotNull(message = "El ID de la marca es requerido.")
    @Positive(message = "El ID de la marca debe ser un número positivo.")
    private int brandId;

    /**
     * Name of the asset status (e.g., AVAILABLE, LOANED).
     * Cannot be blank.
     */
    @NotBlank(message = "El status del activo es requerido.")
    private String statusName;

    /**
     * Serial number of the asset.
     * Cannot be blank and must not exceed 255 characters.
     */
    @NotBlank(message = "La serie del activo es requerida.")
    @Size(max = 255, message = "La serie del activo no debe exceder los 255 caracteres.")
    private String assetSeries;

    /**
     * Plate number of the asset.
     * Cannot be blank and must not exceed 255 characters.
     */
    @NotBlank(message = "El número de placa del activo es requerido.")
    @Size(max = 255, message = "El número de placa del activo no debe exceder los 255 caracteres.")
    private String plateNumber;

    /**
     * ID of the model of the asset.
     * Must be positive and not null.
     */
    @NotNull(message = "El ID del modelo del activo es requerido.")
    @Positive(message = "El ID del modelo del activo debe ser un número positivo.")
    private int assetModelId;

    /**
     * Currency name associated with the asset value (e.g., USD, COLON).
     * Cannot be blank.
     */
    @NotBlank(message = "El nombre de la moneda es requerido.")
    private String currencyName;

    /**
     * ID of the location where the asset is stored.
     * Must be positive and not null.
     */
    @NotNull(message = "El ID de la ubicación es requerido.")
    @Positive(message = "El ID de la ubicación debe ser un número positivo.")
    private int locationNumberId;
}
