package com.impact.core.module.product.payload.request;

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
public class ProductRequest {

    @NotBlank(message = "El nombre del producto es requerido.")
    private String name;

    @NotNull(message = "La fecha de compra es requerida.")
    @PastOrPresent(message = "La fecha de compra no puede ser en el futuro.")
    private LocalDate purchaseDate;

    @NotNull(message = "La fecha de vencimiento es requerida.")
    @Future(message = "La fecha de vencimiento debe ser en el futuro.")
    private LocalDate expiryDate;

    @NotNull(message = "El id de la categoría es requerido.")
    private int categoryId;

    @NotBlank(message = "El nombre del producto es requerido.")
    private String statusName;

}
