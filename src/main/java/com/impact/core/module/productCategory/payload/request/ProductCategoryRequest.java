package com.impact.core.module.productCategory.payload.request;

import com.impact.core.module.productCategory.entity.ProductCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Data Transfer Object (DTO) used for creating or updating a {@link ProductCategory}.
 * Contains the fields for {@link ProductCategory}'s name, minimum quantity, category type ID,
 * and unit of measurement ID.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryRequest {

    /**
     * The name of the product category.
     * It must not be blank and is required for creating or updating a {@link ProductCategory}.
     */
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    /**
     * The minimum quantity of the product category.
     * It must be at least 1 and is required for creating or updating a {@link ProductCategory}.
     */
    @Min(value = 1, message = "La cantidad mínima no puede ser menor a 1")
    private int minimumQuantity;

    /**
     * The ID of the category type associated with the product category.
     * It must be at least 1 and is required for creating or updating a {@link ProductCategory}.
     */
    @Min(value = 1, message = "El id de la categoría no puede ser menor a 1")
    private int categoryTypeId;

    /**
     * The ID of the unit of measurement associated with the product category.
     * It must be at least 1 and is required for creating or updating a {@link ProductCategory}.
     */
    @Min(value = 1, message = "El id de la unidad de medida no puede ser menor a 1")
    private int unitOfMeasurementId;
}
