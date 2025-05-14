package com.impact.core.module.productCategory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Represents a product category in the system.
 * This entity is mapped to the {@code product_category} table in the database.
 */
@Getter
@Setter
@Builder
@Entity
@Table(name = "product_category")
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategory {

    /**
     * The unique identifier of the product category.
     * This field is the primary key and is auto-generated.
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The name of the product category.
     * This field has a maximum length of 100 characters.
     */
    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    /**
     * The minimum quantity required for the product category.
     * This field is required and cannot be null.
     */
    @NotNull
    @Column(name = "minimum_quantity", nullable = false)
    private Integer minimumQuantity;

    /**
     * The category type associated with this product category.
     * This field is a many-to-one relationship with {@link CategoryType}.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_type")
    private CategoryType categoryType;

    /**
     * The unit of measurement associated with this product category.
     * This field is a many-to-one relationship with {@link UnitOfMeasurement}.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_of_measurement")
    private UnitOfMeasurement unitOfMeasurement;

}