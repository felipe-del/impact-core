package com.impact.core.module.productCategory.payload.response;

import com.impact.core.module.productCategory.entity.CategoryType;
import lombok.*;

/**
 * Data Transfer Object (DTO) representing the response for a {@link CategoryType}.
 * Contains the fields for the {@link CategoryType}'s ID, name, and description.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryTypeResponse {

    /**
     * The unique identifier of the category type.
     */
    private int id;

    /**
     * The name of the category type.
     */
    private String name;

    /**
     * The description of the category type.
     */
    private String description;
}
