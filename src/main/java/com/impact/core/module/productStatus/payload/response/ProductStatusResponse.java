package com.impact.core.module.productStatus.payload.response;

import lombok.*;

/**
 * Response DTO used for sending product status details to the frontend.
 * <p>
 * This class includes the essential information about a product's status,
 * such as its identifier, name, and description. It is used for displaying
 * product status data in responses sent to the client.
 * </p>
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductStatusResponse {

    /**
     * The unique identifier of the product status.
     */
    private Integer id;

    /**
     * The name of the product status.
     */
    private String name;

    /**
     * A detailed description of the product status.
     */
    private String description;
}
