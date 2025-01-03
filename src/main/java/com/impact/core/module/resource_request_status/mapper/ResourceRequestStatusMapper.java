package com.impact.core.module.resource_request_status.mapper;

import com.impact.core.module.resource_request_status.entity.ResourceRequestStatus;
import com.impact.core.module.resource_request_status.payload.response.ResourceRequestStatusResponse;
import org.springframework.stereotype.Component;

@Component
public class ResourceRequestStatusMapper {

    public ResourceRequestStatusResponse toDTO(ResourceRequestStatus resourceRequestStatus) {
        return ResourceRequestStatusResponse.builder()
                .id(resourceRequestStatus.getId())
                .name(resourceRequestStatus.getName().toString())
                .description(resourceRequestStatus.getDescription())
                .build();
    }
}
