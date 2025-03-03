package com.impact.core.module.spaceRequest_Reservation.entity;

import com.impact.core.module.resource_request_status.entity.ResourceRequestStatus;
import com.impact.core.module.space.entity.Space;
import com.impact.core.module.spaceStatus.entity.SpaceStatus;
import com.impact.core.module.spaceStatus.enun.ESpaceStatus;
import com.impact.core.module.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Builder
@Entity
@Table(name = "space_request")
@NoArgsConstructor
@AllArgsConstructor
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
    private SpaceStatus status;

    @ColumnDefault("0")
    @Column(name = "use_equipment")
    private Boolean useEquipment;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}