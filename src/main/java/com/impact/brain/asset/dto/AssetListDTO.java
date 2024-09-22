package com.impact.brain.asset.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class AssetListDTO {
    int id;
    String plate;             // Added field
    String category;          // Added field
    String subcategory;       // Ensure this matches with your model
    String status;

    @Override
    public String toString() {
        return "AssetListDTO{" +
                "id=" + id +
                ", plate='" + plate + '\'' +            // Adjusted for plate
                ", category='" + category + '\'' +      // Adjusted for category
                ", subcategory='" + subcategory + '\'' + // Ensured it's correct
                ", status='" + status + '\'' +
                '}';
    }
}
