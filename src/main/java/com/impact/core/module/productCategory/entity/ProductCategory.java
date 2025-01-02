package com.impact.core.module.productCategory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "product_category")
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategory {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @NotNull
    @Column(name = "minimum_quantity", nullable = false)
    private Integer minimumQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_type")
    private CategoryType categoryType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_of_measurement")
    private UnitOfMeasurement unitOfMeasurement;

}