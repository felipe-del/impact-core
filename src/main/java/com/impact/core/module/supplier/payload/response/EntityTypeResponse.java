package com.impact.core.module.supplier.payload.response;

import lombok.*;

/**
 * Entity Type Data Transfer Object (DTO) used for returning information about the type of a supplier entity.
 * <p>
 * This class is typically used in response payloads where entity classification (e.g., PHYSICAL or LEGAL) needs to be presented.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntityTypeResponse {

    /**
     * Unique identifier for the Entity Type.
     */
    private int id;

    /**
     * Name representing the type of entity.
     * <p>
     * Common values include "PHYSICAL" or "LEGAL".
     */
    private String typeName;
}
