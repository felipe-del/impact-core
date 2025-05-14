package com.impact.core.module.productRequestStatistics.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;

/**
 * Entity class representing a mapping to the "product_entries_by_date" database view.
 * <p>
 * This class is used to represent the data retrieved from the database view "product_entries_by_date" and contains the following
 * fields: category ID, total income, and purchase date. The class is annotated with {@link Immutable}, indicating that it maps
 * to a database view rather than a regular table, and its data cannot be updated.
 * </p>
 */
@Getter
@Setter
@Entity
@Immutable
@Table(name = "product_entries_by_date")
public class ProductEntriesByDate {

    /**
     * The unique identifier for the category.
     */
    @Id
    @Column(name = "category_id")
    private Integer categoryId;

    /**
     * The total income generated for the specific category.
     */
    @NotNull
    @Column(name = "total_ingresos", nullable = false)
    private Long totalIngresos;

    /**
     * The date of the purchase for the category.
     */
    @Column(name = "purchase_date")
    private LocalDate purchaseDate;
}