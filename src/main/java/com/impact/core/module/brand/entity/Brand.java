package com.impact.core.module.brand.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Entity class representing a brand.
 * This class is mapped to the "brand" table in the database.
 */
@Getter
@Setter
@Builder
@Entity
@Table(name = "brand")
@NoArgsConstructor
@AllArgsConstructor
public class Brand {

    /**
     * Unique identifier for the brand.
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The name of the brand.
     */
    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

}