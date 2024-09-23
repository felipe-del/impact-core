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
    private Integer subcategoryId;
    private Integer brandId;
    private Integer statusId;
    private Boolean isDeleted;
    private String assetSeries;
    private String plateNumber;
    private Integer currencyId;
    private Integer assetModelId;
    private Integer locationNumber;

    @Override
    public String toString() {
        return "AssetDTO{" +
                "id=" + id +
                ", purchaseDate=" + purchaseDate +
                ", value=" + value +
                ", responsibleId=" + responsibleId +
                ", supplierId=" + supplierId +
                ", subcategoryId=" + subcategoryId +
                ", brandId=" + brandId +
                ", statusId=" + statusId +
                ", isDeleted=" + isDeleted +
                ", assetSeries='" + assetSeries + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
                ", currencyName='" + currencyId+ '\'' +
                ", assetModelName='" + assetModelId + '\'' +
                '}';
    }
}
