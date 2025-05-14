package com.impact.core.module.space.entity;

import com.impact.core.module.buildingLocation.entity.BuildingLocation;
import com.impact.core.module.spaceStatus.entity.SpaceStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalTime;

/**
 * Entity class that represents a physical space within the system.
 * <p>
 * This class includes basic attributes of a space such as its name, code, associated location,
 * capacity, operational hours, and current status.
 */
@Getter
@Setter
@Builder
@Entity
@Table(name = "space")
@NoArgsConstructor
@AllArgsConstructor
public class Space {

    /**
     * Unique identifier for the space.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * Name of the space.
     * This field must not be null and has a maximum length of 100 characters.
     */
    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * Code used to identify the space in a simplified numeric format.
     */
    @Column(name = "space_code")
    private Integer spaceCode;

    /**
     * Reference to the {@link BuildingLocation} where the space is physically located.
     * This association is mandatory.
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "location_id", nullable = false)
    private BuildingLocation location;

    /**
     * Maximum number of people allowed in the space.
     */
    @Column(name = "max_people")
    private Integer maxPeople;

    /**
     * Opening time of the space in 24-hour format (HH:mm).
     */
    @NotNull
    @Column(name = "open_time", nullable = false)
    private LocalTime openTime;

    /**
     * Closing time of the space in 24-hour format (HH:mm).
     */
    @NotNull
    @Column(name = "close_time", nullable = false)
    private LocalTime closeTime;

    /**
     * Current operational status of the space.
     * Linked to a {@link SpaceStatus} entity.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private SpaceStatus status;

    /**
     * Indicates whether the space is marked as deleted.
     * This field uses a default value of {@code false}.
     */
    @ColumnDefault("0")
    @Column(name = "is_deleted")
    private Boolean isDeleted;

}