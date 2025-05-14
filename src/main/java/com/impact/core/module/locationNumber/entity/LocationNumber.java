package com.impact.core.module.locationNumber.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Represents a location number entity that is mapped to the "location_number" table in the database.
 * <p>
 * This class is used to store the location number associated with a specific location type. The location number
 * corresponds to a unique identifier within a particular location type.
 * </p>
 */
@Getter
@Setter
@Builder
@Entity
@Table(name = "location_number")
@NoArgsConstructor
@AllArgsConstructor
public class LocationNumber {

    /**
     * The unique identifier for a location number.
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The location type associated with this location number.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_type_id")
    private LocationType locationType;

    /**
     * The location number.
     */
    @Column(name = "location_number")
    private Integer locationNumber;

}