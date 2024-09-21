package com.impact.brain.asset.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class AssetListDTO {
    String plate;
    String category;
    String subcategory;
    String status;
    String description;

    @Override
    public String toString() {
        return "AssetListDTO{" +
                "plate=" + plate +
                ", category=" + category +
                ", subcategory=" + subcategory +
                ", status=" + status +
                ", description=" + description +
                '}';
    }
}
