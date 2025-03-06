package com.impact.core.module.assetRequest.payload.renew;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssetRequestDTORenew {
    @NotNull(message = "El id del activo es requerido")
    @Positive(message = "El id del activo debe ser un número positivo")
    private int assetId;
    @NotBlank(message = "La razón es requerida")
    private String reason;
    @NotNull(message = "La fecha de expiración de petiticion es requerida")
    @Future(message = "La fecha de expiración de la petición debe ser futura")
    private LocalDate expirationDate;
    @NotNull(message = "La fecha de creación es requerida")
    private LocalDate createdAt;
}
