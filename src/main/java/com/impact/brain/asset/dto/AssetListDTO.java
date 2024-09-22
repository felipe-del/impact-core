package com.impact.brain.asset.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class AssetListDTO {
    int id;                  // Assuming this is still needed
    String plate;           // New field from master branch
    String category;        // New field from master branch
    String subcategory;     // Existing field from feature branch
    String status;
    String description;

    @Override
    public String toString() {
        return "AssetListDTO{" +
                "id=" + id +                 // Keeping the id field
                ", plate=" + plate +         // Added plate field
                ", category=" + category +   // Added category field
                ", subcategory=" + subcategory +
                ", status=" + status +
                ", description=" + description +
                '}';
    }
}
