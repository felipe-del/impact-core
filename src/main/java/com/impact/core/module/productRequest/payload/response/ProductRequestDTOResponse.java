package com.impact.core.module.productRequest.payload.response;

import com.impact.core.module.product.payload.response.ProductResponseDTO;
import com.impact.core.module.resource_request_status.payload.response.ResourceRequestStatusResponse;
import com.impact.core.module.user.payload.UserResponse;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTOResponse {
    private int id;
    private ProductResponseDTO product;
    private ResourceRequestStatusResponse status;
    private String reason;
    private UserResponse user;
}
