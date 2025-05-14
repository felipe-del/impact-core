package com.impact.core.module.asset.payload.response;

import com.impact.core.module.assetModel.payload.response.AssetModelResponse;
import com.impact.core.module.assetStatus.payload.response.AssetStatusResponse;
import com.impact.core.module.assetSubcategory.payload.response.AssetCategoryResponse;
import com.impact.core.module.assetSubcategory.payload.response.AssetSubcategoryResponse;
import com.impact.core.module.brand.payload.response.BrandResponse;
import com.impact.core.module.currency.payload.response.CurrencyResponse;
import com.impact.core.module.locationNumber.payload.response.LocationNumberResponse;
import com.impact.core.module.supplier.payload.response.SupplierResponse;
import com.impact.core.module.user.entity.User;
import com.impact.core.module.user.payload.response.UserResponse;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for sending detailed information about an Asset.
 * Used in responses across the Asset module.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssetResponse {
    /**
     * Unique identifier of the asset.
     */
    private int id;

    /**
     * Date the asset was purchased.
     */
    private LocalDate purchaseDate;

    /**
     * Monetary value of the asset
     */
    private BigDecimal value;

    /**
     * The user responsible or associated with the asset.
     */
    private UserResponse user;

    /**
     * The supplier from whom the asset was acquired.
     */
    private SupplierResponse supplier;

    /**
     * The subcategory the asset belongs to.
     */
    private AssetSubcategoryResponse subcategory;

    /**
     * The broader category the subcategory is part of.
     */
    private AssetCategoryResponse category;

    /**
     * The brand or manufacturer of the asset.
     */
    private BrandResponse brand;

    /**
     * Current status of the asset (e.g., Available, Loaned).
     */
    private AssetStatusResponse status;

    /**
     * Serial number of the asset.
     */
    private String assetSeries;

    /**
     * Plate number or tag of the asset.
     */
    private String plateNumber;

    /**
     * Model information of the asset.
     */
    private AssetModelResponse model;

    /**
     * Currency in which the asset’s value is expressed.
     */
    private CurrencyResponse currency;

    /**
     * Location where the asset is stored or assigned.
     */
    private LocationNumberResponse locationNumber;
}
