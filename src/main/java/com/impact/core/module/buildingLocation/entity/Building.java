package com.impact.core.module.buildingLocation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Entity class representing a building.
 * <p>
 * This class maps to the "building" table in the database and contains information about a building, such as its unique ID and name.
 * </p>
 */
@Getter
@Setter
@Builder
@Entity
@Table(name = "building")
@NoArgsConstructor
@AllArgsConstructor
public class Building {

    /**
     * The unique identifier of the building.
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The name of the building.
     */
    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

}