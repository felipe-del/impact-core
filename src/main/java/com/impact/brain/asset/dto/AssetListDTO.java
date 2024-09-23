package com.impact.brain.asset.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AssetListDTO {
    int id;
    String plate;
    String category;
    String subcategory;
    String status;
    String description;
    String location;

    @Override
    public String toString() {
        return "AssetListDTO{" +
                "id=" + id +
                ", subcategory=" + subcategory +
                ", plate=" + plate +
                ", category=" + category +
                ", location=" + location +
                ", description=" + description +
                ", status=" + status +
                '}';
    }
}
