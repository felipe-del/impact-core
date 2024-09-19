package com.impact.brain.asset.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Isaac F. B. C.
 * @since 9/14/2024 - 6:38 AM
 */

@Setter
@Getter
public class AssetDTO {

    private Integer id;
    private LocalDate purchaseDate;
    private BigDecimal value;
    private Integer responsibleId;
    private Integer supplierId;
    private Integer categoryId;
    private Integer brandId;
    private Integer statusId;
    private Boolean isDeleted;
    private String assetSeries;
    private String plateNumber;
    private String currencyName;
    private String assetModelName;

    @Override
    public String toString() {
        return "AssetDTO{" +
                "id=" + id +
                ", purchaseDate=" + purchaseDate +
                ", value=" + value +
                ", responsibleId=" + responsibleId +
                ", supplierId=" + supplierId +
                ", categoryId=" + categoryId +
                ", brandId=" + brandId +
                ", statusId=" + statusId +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
