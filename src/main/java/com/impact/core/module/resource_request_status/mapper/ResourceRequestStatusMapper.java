package com.impact.core.module.resource_request_status.mapper;

import com.impact.core.module.resource_request_status.entity.ResourceRequestStatus;
import com.impact.core.module.resource_request_status.payload.response.ResourceRequestStatusResponse;
import org.springframework.stereotype.Component;

/**
 * Mapper class responsible for transforming {@link ResourceRequestStatus} entities into {@link ResourceRequestStatusResponse} DTOs.
 * <p>
 * This class provides methods for converting data between the entity and the response model used in the API layer.
 */
@Component
public class ResourceRequestStatusMapper {

    /**
     * Converts a {@link ResourceRequestStatus} entity to a {@link ResourceRequestStatusResponse} DTO.
     * <p>
     * This method maps the fields of the {@link ResourceRequestStatus} entity (such as ID, name, and description) to the corresponding
     * fields of the {@link ResourceRequestStatusResponse}.
     *
     * @param resourceRequestStatus the entity to convert
     * @return a {@link ResourceRequestStatusResponse} containing the mapped data
     */
    public ResourceRequestStatusResponse toDTO(ResourceRequestStatus resourceRequestStatus) {
        return ResourceRequestStatusResponse.builder()
                .id(resourceRequestStatus.getId())
                .name(resourceRequestStatus.getName().toString())
                .description(resourceRequestStatus.getDescription())
                .build();
    }
}
