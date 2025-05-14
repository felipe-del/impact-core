package com.impact.core.module.productCategory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Represents a category type in the system.
 * This entity is mapped to the {@code category_type} table in the database.
 */
@Getter
@Setter
@Builder
@Entity
@Table(name = "category_type")
@NoArgsConstructor
@AllArgsConstructor
public class CategoryType {

    /**
     * The unique identifier of the category type.
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The name of the category type.
     */
    @Size(max = 50)
    @NotNull
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    /**
     * A description of the category type.
     */
    @Size(max = 100)
    @NotNull
    @Column(name = "description", nullable = false, length = 100)
    private String description;

}