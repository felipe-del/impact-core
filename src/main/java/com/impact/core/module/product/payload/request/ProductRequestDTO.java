package com.impact.core.module.product.payload.request;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {

    @NotNull(message = "La cantidad es requerida.")
    @Positive(message = "La cantidad debe ser mayor a 0.")
    private int quantity;

    @NotNull(message = "La fecha de compra es requerida.")
    @PastOrPresent(message = "La fecha de compra no puede ser en el futuro.")
    private LocalDate purchaseDate;

    @Future(message = "La fecha de vencimiento debe ser en el futuro.")
    @Nullable
    private LocalDate expiryDate;

    @NotNull(message = "El id de la categoría es requerido.")
    private int categoryId;

    @NotBlank(message = "El nombre del producto es requerido.")
    private String statusName;

}
