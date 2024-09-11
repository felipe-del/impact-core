package com.impact.brain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product_category")
public class ProductCategory {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @NotNull
    @Column(name = "cantidad_minima", nullable = false)
    private Integer cantidadMinima;

    @Size(max = 50)
    @NotNull
    @Column(name = "product_type", nullable = false, length = 50)
    private String productType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorie_type")
    private CategorieType categorieType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_of_measurement")
    private UnitOfMeasurement unitOfMeasurement;

}