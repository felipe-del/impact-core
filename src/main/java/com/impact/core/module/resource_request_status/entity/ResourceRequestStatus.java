package com.impact.core.module.resource_request_status.entity;

import com.impact.core.module.resource_request_status.enun.EResourceRequestStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "resource_request_status")
public class ResourceRequestStatus {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "name", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private EResourceRequestStatus name;

    @Lob
    @Column(name = "description")
    private String description;

}