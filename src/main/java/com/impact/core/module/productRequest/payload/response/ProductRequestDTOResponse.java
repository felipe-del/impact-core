package com.impact.core.module.productRequest.payload.response;

import com.impact.core.module.product.entity.Product;
import com.impact.core.module.product.payload.response.ProductResponseDTO;
import com.impact.core.module.productRequest.entity.ProductRequest;
import com.impact.core.module.resource_request_status.payload.response.ResourceRequestStatusResponse;
import com.impact.core.module.user.entity.User;
import com.impact.core.module.user.payload.response.UserResponse;
import lombok.*;

/**
 * Data Transfer Object (DTO) for returning details of a {@link ProductRequest}.
 * <p>
 * This class is used to structure the response data for a {@link ProductRequest}, including details such as the
 * associated {@link Product}, the status of the request, the reason for the request, the user who made the request,
 * and the timestamp of when the request was created.
 * </p>
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTOResponse {

    /**
     * The unique identifier for the {@link ProductRequest}.
     */
    private int id;

    /**
     * The {@link ProductResponseDTO} representing the {@link Product} associated with the {@link ProductRequest}.
     */
    private ProductResponseDTO product;

    /**
     * The {@link ResourceRequestStatusResponse} representing the current status of the {@link ProductRequest}.
     */
    private ResourceRequestStatusResponse status;

    /**
     * The reason provided for the {@link ProductRequest}.
     */
    private String reason;

    /**
     * The {@link UserResponse} representing the {@link User} who made the {@link ProductRequest}.
     */
    private UserResponse user;

    /**
     * The timestamp indicating when the {@link ProductRequest} was created, formatted as a string.
     */
    private String createdAt;
}
