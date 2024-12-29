package com.impact.core.module.space.entity;

import com.impact.core.module.buildingLocation.entity.BuildingLocation;
import com.impact.core.module.spaceStatus.entity.SpaceStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalTime;

@Getter
@Setter
@Builder
@Entity
@Table(name = "space")
@NoArgsConstructor
@AllArgsConstructor
public class Space {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "space_code")
    private Integer spaceCode;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "location_id", nullable = false)
    private BuildingLocation location;

    @Column(name = "max_people")
    private Integer maxPeople;

    @NotNull
    @Column(name = "open_time", nullable = false)
    private LocalTime openTime;

    @NotNull
    @Column(name = "close_time", nullable = false)
    private LocalTime closeTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private SpaceStatus status;

    @ColumnDefault("0")
    @Column(name = "is_deleted")
    private Boolean isDeleted;

}