package com.impact.brain.products.dto;

public class ProductCategoryDTO {
    private int id;
    private String name;
    private int cantidadMinima;


    private int categoryType;
    private int unit_of_measurement;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUnit_of_measurement() {
        return unit_of_measurement;
    }

    public void setUnit_of_measurement(int unit_of_measurement) {
        this.unit_of_measurement = unit_of_measurement;
    }

    public int getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(int categoryType) {
        this.categoryType = categoryType;
    }

    public int getCantidadMinima() {
        return cantidadMinima;
    }

    public void setCantidadMinima(int cantidadMinima) {
        this.cantidadMinima = cantidadMinima;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ProductCategoryDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cantidadMinima=" + cantidadMinima +
                ", categoryType=" + categoryType +
                ", unit_of_measurement=" + unit_of_measurement +
                '}';
    }
}
