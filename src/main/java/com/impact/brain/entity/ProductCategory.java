package com.impact.brain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product_category")
public class ProductCategory {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "cantidad_minima", nullable = false)
    private Integer cantidadMinima;

    @Column(name = "product_type", nullable = false, length = 50)
    private String productType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorie_type")
    private CategorieType categorieType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_of_measurement")
    private UnitOfMeasurement unitOfMeasurement;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCantidadMinima() {
        return cantidadMinima;
    }

    public void setCantidadMinima(Integer cantidadMinima) {
        this.cantidadMinima = cantidadMinima;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public CategorieType getCategorieType() {
        return categorieType;
    }

    public void setCategorieType(CategorieType categorieType) {
        this.categorieType = categorieType;
    }

    public UnitOfMeasurement getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(UnitOfMeasurement unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

}