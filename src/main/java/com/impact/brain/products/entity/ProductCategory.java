package com.impact.brain.products.entity;

import com.impact.brain.products.entity.CategorieType;
import com.impact.brain.products.entity.UnitOfMeasurement;
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

//    @Column(name = "product_type", nullable = false, length = 50)
//    private String productType;

    @ManyToOne
    @JoinColumn(name = "categorie_type", referencedColumnName = "id")
    private CategorieType categorieType;

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

//    public String getProductType() {
//        return productType;
//    }
//
//    public void setProductType(String productType) {
//        this.productType = productType;
//    }

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

    @Override
    public String toString() {
        return "ProductCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cantidadMinima=" + cantidadMinima +
                ", categorieType=" + categorieType +
                ", unitOfMeasurement=" + unitOfMeasurement +
                '}';
    }
}