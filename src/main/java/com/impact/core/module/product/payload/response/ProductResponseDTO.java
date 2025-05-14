package com.impact.core.module.product.payload.response;

import com.impact.core.module.productCategory.payload.response.ProductCategoryResponse;
import com.impact.core.module.productStatus.payload.response.ProductStatusResponse;
import lombok.*;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) representing the response for a product.
 * <p>
 * This class contains the details of a product, including its identifier,
 * purchase and expiration dates, as well as the associated category and status.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {

    /**
     * The unique identifier of the product.
     */
    private int id;

    /**
     * The date when the product was purchased.
     */
    private LocalDate purchaseDate;

    /**
     * The expiration date of the product.
     */
    private LocalDate expiryDate;

    /**
     * The category of the product.
     * <p>
     * This field represents the {@link ProductCategoryResponse}
     * that the product belongs to.
     */
    private ProductCategoryResponse category;

    /**
     * The status of the product.
     * <p>
     * This field represents the {@link ProductStatusResponse}
     * indicating the current status of the product.
     */
    private ProductStatusResponse status;
}
