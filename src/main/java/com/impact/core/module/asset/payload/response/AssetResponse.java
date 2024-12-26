package com.impact.core.module.asset.payload.response;

import com.impact.core.module.assetModel.payload.response.AssetModelResponse;
import com.impact.core.module.assetStatus.entity.AssetStatus;
import com.impact.core.module.assetStatus.payload.response.AssetStatusResponse;
import com.impact.core.module.assetSubcategory.payload.response.AssetCategoryResponse;
import com.impact.core.module.assetSubcategory.payload.response.AssetSubcategoryResponse;
import com.impact.core.module.brand.payload.response.BrandResponse;
import com.impact.core.module.currency.payload.response.CurrencyResponse;
import com.impact.core.module.locationNumber.payload.response.LocationNumberResponse;
import com.impact.core.module.supplier.payload.response.SupplierResponse;
import com.impact.core.module.user.payload.UserResponse;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssetResponse {
    private int id;
    private LocalDate purchaseDate;
    private BigDecimal value;
    private UserResponse user;
    private SupplierResponse supplier;
    private AssetSubcategoryResponse subcategory;
    private AssetCategoryResponse category;
    private BrandResponse brand;
    private AssetStatusResponse status;
    private String assetSeries;
    private String plateNumber;
    private AssetModelResponse model;
    private CurrencyResponse currency;
    private LocationNumberResponse locationNumber;


}
