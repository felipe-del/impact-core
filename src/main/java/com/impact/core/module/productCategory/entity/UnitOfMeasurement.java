package com.impact.core.module.productCategory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Represents a unit of measurement in the system.
 * This entity is mapped to the {@code unit_of_measurement} table in the database.
 */
@Getter
@Setter
@Builder
@Entity
@Table(name = "unit_of_measurement")
@NoArgsConstructor
@AllArgsConstructor
public class UnitOfMeasurement {

    /**
     * The unique identifier of the unit of measurement.
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The name of the unit of measurement.
     */
    @Size(max = 50)
    @NotNull
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    /**
     * The abbreviation of the unit of measurement.
     */
    @Size(max = 10)
    @Column(name = "abbreviation", length = 10)
    private String abbreviation;

}