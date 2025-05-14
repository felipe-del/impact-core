package com.impact.core.module.resource_request_status.entity;

import com.impact.core.module.resource_request_status.enun.EResourceRequestStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity representing the status of a resource request.
 * <p>
 * This entity stores the status information for a resource request, including the name of the status
 * (represented as an enum), a description of the status, and the unique identifier for the status.
 */
@Getter
@Setter
@Entity
@Table(name = "resource_request_status")
public class ResourceRequestStatus {

    /**
     * Unique identifier for the resource request status.
     */
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * Name of the resource request status.
     * <p>
     * The status name is stored as an enum value of type {@link EResourceRequestStatus}.
     * It defines the current state of a resource request, such as pending, approved, or rejected.
     */
    @Size(max = 50)
    @NotNull
    @Column(name = "name", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private EResourceRequestStatus name;

    /**
     * Description of the resource request status.
     */
    @Lob
    @Column(name = "description")
    private String description;

}