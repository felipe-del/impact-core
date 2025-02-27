package com.impact.core.module.asset.payload.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssetRequest {
    @NotNull(message = "La fecha de compra es requerida.")
    @PastOrPresent(message = "La fecha de compra no puede ser en el futuro.")
    private LocalDate purchaseDate;

    @NotNull(message = "El valor del activo es requerido.")
    @Positive(message = "El valor del activo debe ser un número positivo.")
    private BigDecimal value;

    @NotNull(message = "El ID del usuario es requerido.")
    @Positive(message = "El ID del usuario debe ser un número positivo.")
    private int userId;

    @NotNull(message = "El ID del proveedor es requerido.")
    @Positive(message = "El ID del proveedor debe ser un número positivo.")
    private int supplierId;

    @NotNull(message = "El ID de la subcategoría es requerido.")
    @Positive(message = "El ID de la subcategoría debe ser un número positivo.")
    private int subCategoryId;

    @NotNull(message = "El ID de la marca es requerido.")
    @Positive(message = "El ID de la marca debe ser un número positivo.")
    private int brandId;

    @NotBlank(message = "El status del activo es requerido.")
    private String statusName;

    @NotBlank(message = "La serie del activo es requerida.")
    @Size(max = 255, message = "La serie del activo no debe exceder los 255 caracteres.")
    private String assetSeries;

    @NotBlank(message = "El número de placa del activo es requerido.")
    @Size(max = 255, message = "El número de placa del activo no debe exceder los 255 caracteres.")
    private String plateNumber;

    @NotNull(message = "El ID del modelo del activo es requerido.")
    @Positive(message = "El ID del modelo del activo debe ser un número positivo.")
    private int assetModelId;

    @NotBlank(message = "El nombre de la moneda es requerido.")
    private String currencyName;

    @NotNull(message = "El ID de la ubicación es requerido.")
    @Positive(message = "El ID de la ubicación debe ser un número positivo.")
    private int locationNumberId;
}
