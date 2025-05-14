package com.impact.core.module.spaceRequest_Reservation.entity;
import com.impact.core.module.resource_request_status.entity.ResourceRequestStatus;
import com.impact.core.module.space.entity.Space;
import com.impact.core.module.spaceStatus.entity.SpaceStatus;
import com.impact.core.module.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

/**
 * Entity class representing a space request for a particular space, including
 * the number of people, event description, and status of the request.
 * <p>
 * The SpaceRequest entity is used to manage requests made by users to reserve
 * or request a space, including handling details such as equipment usage,
 * status, and associated user.
 */
@Getter
@Setter
@Builder
@Entity
@Table(name = "space_request")
@NoArgsConstructor
@AllArgsConstructor
public class SpaceRequest {

    /**
     * Unique identifier for the space request.
     */
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * The space being requested for reservation.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "space_id")
    private Space space;

    /**
     * Number of people attending the event or reservation.
     */
    @Column(name = "num_people")
    private Integer numPeople;

    /**
     * Description of the event associated with the space request.
     */
    @Size(max = 255)
    @Column(name = "event_desc")
    private String eventDesc;

    /**
     * Additional observations or notes for the event associated with the request.
     */
    @Size(max = 255)
    @Column(name = "event_obs")
    private String eventObs;

    /**
     * Status of the space request (e.g., pending, approved).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private ResourceRequestStatus status;

    /**
     * Whether equipment is requested for the event.
     * Default value is false (0).
     */
    @ColumnDefault("0")
    @Column(name = "use_equipment")
    private Boolean useEquipment;

    /**
     * The user who made the space request.
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Timestamp indicating when the space request was created.
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Method to set the createdAt field before persisting the entity.
     * Automatically sets the current time if the field is null.
     */
    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}