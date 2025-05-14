package com.impact.core.module.supplier.payload.response;

import lombok.*;

/**
 * Supplier Data Transfer Object (DTO) used for returning supplier information in response payloads.
 * <p>
 * This class provides detailed information about a supplier including contact and classification details.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierResponse {

    /**
     * Unique identifier of the Supplier.
     */
    private int id;

    /**
     * Full name of the Supplier.
     */
    private String name;

    /**
     * Contact phone number of the Supplier.
     */
    private String phone;

    /**
     * Email address of the Supplier.
     */
    private String email;

    /**
     * Physical address of the Supplier.
     */
    private String address;

    /**
     * Name of the associated Entity Type (e.g., PHYSICAL or LEGAL).
     */
    private String entityTypeName;

    /**
     * Contact person for the client related to the Supplier.
     */
    private String clientContact;

    /**
     * Identification number of the Supplier.
     */
    private String idNumber;
}
