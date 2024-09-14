package com.impact.brain.products.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product_category")
public class ProductCategory {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "cantidad_minima", nullable = false)
    private Integer cantidadMinima;

    @ManyToOne
    @JoinColumn(name = "category_type", referencedColumnName = "id")
    private CategoryType categoryType;

    @ManyToOne
    @JoinColumn(name = "unit_of_measurement", referencedColumnName = "id")
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

    public CategoryType getCategorieType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }

    public UnitOfMeasurement getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(UnitOfMeasurement unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cantidadMinima=" + cantidadMinima +
                ", categorieType=" + categoryType +
                ", unitOfMeasurement=" + unitOfMeasurement +
                '}';
    }
}