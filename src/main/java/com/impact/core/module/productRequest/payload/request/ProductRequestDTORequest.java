package com.impact.core.module.productRequest.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTORequest {
    @NotNull(message = "La cantidad es requerida")
    @PositiveOrZero(message = "La cantidad debe ser mayor o igual a 0")
    private int quantity;
    @NotNull(message = "El id del producto es requerido")
    private int productId;
    @NotBlank(message = "La razón es requerida")
    private String reason;
}
