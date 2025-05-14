package com.impact.core.module.buildingLocation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Entity class representing a building location.
 * <p>
 * This class maps to the "building_location" table in the database and contains information about a specific location within a building,
 * such as the building reference and the floor where the location resides.
 * </p>
 */
@Getter
@Setter
@Builder
@Entity
@Table(name = "building_location")
@NoArgsConstructor
@AllArgsConstructor
public class BuildingLocation {

    /**
     * The unique identifier of the building location.
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The building associated with this location.
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;


    /**
     * The floor of the building where this location is located.
     */
    @Size(max = 50)
    @NotNull
    @Column(name = "floor", nullable = false, length = 50)
    private String floor;

}