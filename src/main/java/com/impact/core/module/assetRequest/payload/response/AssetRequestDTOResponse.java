package com.impact.core.module.assetRequest.payload.response;

import com.impact.core.module.asset.payload.response.AssetResponse;
import com.impact.core.module.resource_request_status.payload.response.ResourceRequestStatusResponse;
import com.impact.core.module.user.payload.response.UserResponse;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssetRequestDTOResponse {
    private int id;
    private AssetResponse asset;
    private ResourceRequestStatusResponse status;
    private String reason;
    private String expirationDate;
    private UserResponse user;
    private String createdAt;
}
