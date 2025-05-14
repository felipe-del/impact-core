package com.impact.core.module.locationNumber.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Represents a location type entity mapped to the "location_type" table in the database.
 * <p>
 * This class is used to store the type of location, which is identified by a unique ID and associated with a
 * specific name.
 * </p>
 */
@Getter
@Setter
@Builder
@Entity
@Table(name = "location_type")
@NoArgsConstructor
@AllArgsConstructor
public class LocationType {

    /**
     * The unique identifier for a location type.
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The name of the location type.
     */
    @Size(max = 100)
    @NotNull
    @Column(name = "type_name", nullable = false, length = 100)
    private String typeName;

}