package com.impact.brain.products.dto;


import lombok.Getter;

import java.time.LocalDate;

public class ProductDTO {
    @Getter
    private Integer id;
    private LocalDate purchaseDate;
    private LocalDate expiryDate;
    private Integer category;
    private Integer status;
    private Integer quantity;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer categorie) {
        this.category = categorie;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", purchaseDate=" + purchaseDate +
                ", expiryDate=" + expiryDate +
                ", categorie=" + category +
                ", status=" + status +
                '}';
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
