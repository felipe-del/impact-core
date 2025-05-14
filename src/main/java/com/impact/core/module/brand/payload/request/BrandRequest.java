package com.impact.core.module.brand.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * DTO (Data Transfer Object) for creating or updating a brand.
 * It contains the request data needed to create or update a brand in the system.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandRequest {

    /**
     * The name of the brand.
     * This field is mandatory and cannot be blank.
     * A validation message will be shown if the field is left empty.
     */
    @NotBlank(message = "El nombre de la marca no puede estar vacío")
    String name;
}
