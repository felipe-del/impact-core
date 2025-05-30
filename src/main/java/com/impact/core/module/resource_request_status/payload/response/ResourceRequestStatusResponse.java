package com.impact.core.module.resource_request_status.payload.response;

import com.impact.core.module.resource_request_status.entity.ResourceRequestStatus;
import com.impact.core.module.resource_request_status.enun.EResourceRequestStatus;
import lombok.*;

/**
 * DTO (Data Transfer Object) class for the response data of a {@link ResourceRequestStatus}.
 * <p>
 * This class is used to transfer the relevant information about a resource request status from the backend
 * to the client or external systems.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceRequestStatusResponse {

    /**
     * The unique identifier for the resource request status.
     * <p>
     * This is the ID of the status, typically generated by the database.
     */
    private Integer id;

    /**
     * The name of the resource request status.
     * <p>
     * This field holds the name of the status, which corresponds to an enum value in {@link EResourceRequestStatus}.
     */
    private String name;

    /**
     * A description of the resource request status.
     * <p>
     * This field contains a textual description of what the status represents.
     */
    private String description;
}
