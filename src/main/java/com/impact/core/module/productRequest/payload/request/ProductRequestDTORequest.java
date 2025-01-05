package com.impact.core.module.productRequest.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTORequest {
    @NotNull(message = "El id del producto es requerido")
    private int productId;
    @NotBlank(message = "La razón es requerida")
    private String reason;
}
