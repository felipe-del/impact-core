package com.impact.brain.asset.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class AssetListDTO {
    int id;
    String category;
    BigDecimal number;
    String status;

    @Override
    public String toString() {
        return "AssetListDTO{" +
                "id=" + id +
                ", category=" + category +
                ", number=" + number +
                ", status=" + status +
                '}';
    }
}
