package com.impact.core.module.brand.payload.response;

import lombok.*;

/**
 * DTO (Data Transfer Object) for the response containing brand information.
 * This object is used to return brand details after a brand is created or fetched.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandResponse {
    /**
     * The unique identifier of the brand.
     */
    int id;

    /**
     * The name of the brand.
     */
    String name;
}
