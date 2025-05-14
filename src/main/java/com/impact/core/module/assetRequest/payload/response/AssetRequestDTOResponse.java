package com.impact.core.module.assetRequest.payload.response;

import com.impact.core.module.asset.payload.response.AssetResponse;
import com.impact.core.module.resource_request_status.payload.response.ResourceRequestStatusResponse;
import com.impact.core.module.user.payload.response.UserResponse;
import lombok.*;

/**
 * Data Transfer Object (DTO) for sending asset request data to the frontend.
 * <p>
 * This DTO is used to represent the details of an asset request, including the associated
 * asset, the request status, the reason for the request, the expiration date, and user details.
 * It is typically used when returning asset request information to the client.
 * </p>
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssetRequestDTOResponse {

    /**
     * The unique identifier of the asset request.
     *
     */
    private int id;

    /**
     * The asset associated with the request.
     */
    private AssetResponse asset;

    /**
     * The current status of the asset request.
     */
    private ResourceRequestStatusResponse status;

    /**
     * The reason for the asset request.
     */
    private String reason;

    /**
     * The expiration date of the asset request.
     */
    private String expirationDate;

    /**
     * The user who made the asset request.
     */
    private UserResponse user;

    /**
     * The date and time when the asset request was created.
     */
    private String createdAt;
}
