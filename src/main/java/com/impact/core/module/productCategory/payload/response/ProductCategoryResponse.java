package com.impact.core.module.productCategory.payload.response;

import com.impact.core.module.productCategory.entity.ProductCategory;
import lombok.*;

/**
 * Data Transfer Object (DTO) representing the response for a {@link ProductCategory}.
 * Contains the fields for the {@link ProductCategory}'s ID, name, minimum quantity, category type, and unit of measurement.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryResponse {

    /**
     * The unique identifier of the product category.
     */
    private int id;

    /**
     * The name of the product category.
     */
    private String name;

    /**
     * The minimum quantity required for the product category.
     */
    private int minimumQuantity;

    /**
     * The category type associated with the product category.
     */
    private CategoryTypeResponse categoryType;

    /**
     * The unit of measurement associated with the product category.
     */
    private UnitOfMeasurementResponse unitOfMeasurement;
}
