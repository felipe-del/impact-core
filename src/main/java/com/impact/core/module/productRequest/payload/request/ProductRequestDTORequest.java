package com.impact.core.module.productRequest.payload.request;

import com.impact.core.module.product.entity.Product;
import com.impact.core.module.productRequest.entity.ProductRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

/**
 * Data Transfer Object (DTO) for creating a {@link ProductRequest}.
 * <p>
 * This class is used to encapsulate the input data for creating a new {@link ProductRequest}.
 * It contains fields for the quantity of the product being requested, the product's ID, and the reason for the request.
 * </p>
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTORequest {
    /**
     * The quantity of the {@link Product} being requested.
     * The value must be greater than or equal to 0.
     * This field cannot be {@code null}.
     */
    @NotNull(message = "La cantidad es requerida")
    @PositiveOrZero(message = "La cantidad debe ser mayor o igual a 0")
    private int quantity;

    /**
     * The ID of the {@link Product} being requested.
     * This field cannot be {@code null}.
     */
    @NotNull(message = "El id del producto es requerido")
    private int productId;

    /**
     * The reason for the product request.
     * This field cannot be {@code null} or blank.
     */
    @NotBlank(message = "La razón es requerida")
    private String reason;
}
