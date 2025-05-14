package com.impact.core.module.product.payload.request;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) representing a request to create or update a product.
 * <p>
 * This class contains the required fields for creating or updating a product,
 * including validation annotations to ensure that the input data is valid.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {

    /**
     * The quantity of the product.
     */
    @NotNull(message = "La cantidad es requerida.")
    @Positive(message = "La cantidad debe ser mayor a 0.")
    private int quantity;

    /**
     * The date the product was purchased.
     */
    @NotNull(message = "La fecha de compra es requerida.")
    @PastOrPresent(message = "La fecha de compra no puede ser en el futuro.")
    private LocalDate purchaseDate;


    /**
     * The expiration date of the product.
     */
    @Future(message = "La fecha de vencimiento debe ser en el futuro.")
    @Nullable
    private LocalDate expiryDate;

    /**
     * The unique identifier for the product's category.
     */
    @NotNull(message = "El id de la categoría es requerido.")
    private int categoryId;

    /**
     * The name of the product's status.
     */
    @NotBlank(message = "El nombre del producto es requerido.")
    private String statusName;

}
