package com.impact.core.module.assetRequest.payload.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssetPetitionDTORequest {
    @NotNull(message = "El id del activo es requerido")
    private int assetId;
    @NotBlank(message = "La razón es requerida")
    private String reason;
    @NotNull(message = "La fecha de expiración de petiticion es requerida")
    @Future(message = "La fecha de expiración de la petición debe ser futura")
    private LocalDate expirationDate;
}
