package com.impact.core.entities;

import com.impact.core.module.resource_request_status.entity.ResourceRequestStatus;
import com.impact.core.module.space.entity.Space;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "space_request")
public class SpaceRequest {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_id")
    private Space space;

    @Column(name = "num_people")
    private Integer numPeople;

    @Size(max = 255)
    @Column(name = "event_desc")
    private String eventDesc;

    @Size(max = 255)
    @Column(name = "event_obs")
    private String eventObs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private ResourceRequestStatus status;

    @ColumnDefault("0")
    @Column(name = "use_equipment")
    private Boolean useEquipment;

}