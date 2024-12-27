package com.impact.core.module.buildingLocation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "building_location")
@NoArgsConstructor
@AllArgsConstructor
public class BuildingLocation {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;

    @Size(max = 50)
    @NotNull
    @Column(name = "floor", nullable = false, length = 50)
    private String floor;

}