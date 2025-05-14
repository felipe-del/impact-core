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
 * Entity class representing a mapping to the "product_request_statistics_by_date" database view.
 * <p>
 * This class is used to represent the data retrieved from the database view "product_request_statistics_by_date" and contains
 * the following fields: category ID, category type, status ID, product request ID, total products requested, and request date.
 * The class is annotated with {@link Immutable}, indicating that it maps to a database view, and its data cannot be updated.
 * </p>
 */
@Getter
@Setter
@Entity
@Immutable
@Table(name = "product_request_statistics_by_date")
public class ProductRequestStatisticsByDate {

    /**
     * The unique identifier for the category.
     */
    @Id
    @NotNull
    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    /**
     * The type of the category.
     */
    @Column(name = "category_type")
    private Integer categoryType;

    /**
     * The unique identifier for the status of the product request.
     */
    @Column(name = "status_id")
    private Integer statusId;

    /**
     * The unique identifier for the product request.
     */
    @NotNull
    @Column(name = "product_request_id", nullable = false)
    private Integer productRequestId;

    /**
     * The total number of products requested.
     */
    @NotNull
    @Column(name = "total_products_requested", nullable = false)
    private Long totalProductsRequested;

    /**
     * The date when the product request was made.
     */
    @Column(name = "request_date")
    private LocalDate requestDate;

}