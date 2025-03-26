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
@Table(name = "product_entries_by_date")
public class ProductEntriesByDate {
    @Id
    @Column(name = "category_id")
    private Integer categoryId;

    @NotNull
    @Column(name = "total_ingresos", nullable = false)
    private Long totalIngresos;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;
}