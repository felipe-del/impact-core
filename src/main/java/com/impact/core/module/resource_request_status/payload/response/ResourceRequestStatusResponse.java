package com.impact.core.module.resource_request_status.payload.response;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceRequestStatusResponse {
    private Integer id;
    private String name;
    private String description;
}
