package com.impact.core.module.productCategory.payload.request;

import com.impact.core.module.productCategory.entity.CategoryType;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Data Transfer Object (DTO) used for creating or updating {@link CategoryType}.
 * Contains the fields for {@link CategoryType}'s name and description.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryTypeRequest {

    /**
     * The name of the category type.
     * It must not be blank and is required for creating or updating a {@link CategoryType}.
     */
    @NotBlank(message = "El nombre es requerido")
    private String name;

    /**
     * The description of the category type.
     * It must not be blank and is required for creating or updating a {@link CategoryType}.
     */
    @NotBlank(message = "La descripción es requerida")
    private String description;
}
