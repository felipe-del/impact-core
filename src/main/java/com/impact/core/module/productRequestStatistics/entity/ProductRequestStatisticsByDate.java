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
 * Mapping for DB view
 */
@Getter
@Setter
@Entity
@Immutable
@Table(name = "product_request_statistics_by_date")
public class ProductRequestStatisticsByDate {
    @Id
    @NotNull
    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    @Column(name = "category_type")
    private Integer categoryType;

    @Column(name = "status_id")
    private Integer statusId;

    @NotNull
    @Column(name = "product_request_id", nullable = false)
    private Integer productRequestId;

    @NotNull
    @Column(name = "total_products_requested", nullable = false)
    private Long totalProductsRequested;

    @Column(name = "request_date")
    private LocalDate requestDate;

}