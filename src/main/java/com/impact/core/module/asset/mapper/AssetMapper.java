package com.impact.core.module.asset.mapper;

import com.impact.core.module.asset.entity.Asset;
import com.impact.core.module.asset.payload.request.AssetRequest;
import com.impact.core.module.asset.payload.response.AssetResponse;
import com.impact.core.module.assetModel.mapper.AssetModelMapper;
import com.impact.core.module.assetModel.service.AssetModelService;
import com.impact.core.module.assetStatus.entity.AssetStatus;
import com.impact.core.module.assetStatus.enun.EAssetStatus;
import com.impact.core.module.assetStatus.mapper.AssetStatusMapper;
import com.impact.core.module.assetStatus.service.AssetStatusService;
import com.impact.core.module.assetSubcategory.mapper.AssetCategoryMapper;
import com.impact.core.module.assetSubcategory.mapper.AssetSubcategoryMapper;
import com.impact.core.module.assetSubcategory.service.AssetSubcategoryService;
import com.impact.core.module.brand.mapper.BrandMapper;
import com.impact.core.module.brand.service.BrandService;
import com.impact.core.module.currency.entity.Currency;
import com.impact.core.module.currency.enun.ECurrency;
import com.impact.core.module.currency.mapper.CurrencyMapper;
import com.impact.core.module.currency.service.CurrencyService;
import com.impact.core.module.locationNumber.mapper.LocationNumberMapper;
import com.impact.core.module.locationNumber.service.LocationNumberService;
import com.impact.core.module.supplier.mapper.SupplierMapper;
import com.impact.core.module.supplier.service.SupplierService;
import com.impact.core.module.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssetMapper {

    // SERVICES
    public final UserService userService;
    public final SupplierService supplierService;
    public final AssetSubcategoryService assetSubcategoryService;
    public final BrandService brandService;
    public final AssetStatusService assetStatusService;
    public final AssetModelService assetModelService;
    public final CurrencyService currencyService;
    public final LocationNumberService locationNumberService;
    // MAPPER
    public final SupplierMapper supplierMapper;
    public final AssetSubcategoryMapper assetSubcategoryMapper;
    public final BrandMapper brandMapper;
    public final AssetStatusMapper assetStatusMapper;
    public final AssetModelMapper assetModelMapper;
    public final CurrencyMapper currencyMapper;
    public final LocationNumberMapper locationNumberMapper;
    private final AssetCategoryMapper assetCategoryMapper;


    public Asset toEntity(AssetRequest assetRequest) {
        return Asset.builder()
                .purchaseDate(assetRequest.getPurchaseDate())
                .value(assetRequest.getValue())
                .responsible(userService.findById(assetRequest.getUserId()))
                .supplier(supplierService.findById(assetRequest.getSupplierId()))
                .subcategory(assetSubcategoryService.findById(assetRequest.getSubCategoryId()))
                .brand(brandService.findById(assetRequest.getBrandId()))
                .status(this.getAssetStatusByName(assetRequest.getStatusName()))
                .isDeleted(false)
                .assetSeries(assetRequest.getAssetSeries())
                .plateNumber(assetRequest.getPlateNumber())
                .assetModel(assetModelService.findById(assetRequest.getAssetModelId()))
                .currency(this.getCurrencyByName(assetRequest.getCurrencyName()))
                .locationNumber(locationNumberService.findById(assetRequest.getLocationNumberId()))
                .build();
    }

    public AssetResponse toDTO (Asset asset) {
        return AssetResponse.builder()
                .id(asset.getId())
                .purchaseDate(asset.getPurchaseDate())
                .value(asset.getValue())
                .user(userService.toDTO(asset.getResponsible()))
                .supplier(supplierMapper.toDTO(asset.getSupplier()))
                .subcategory(assetSubcategoryMapper.toDTO(asset.getSubcategory()))
                .category(assetCategoryMapper.toDTO(asset.getSubcategory().getCategory()))
                .brand(brandMapper.toDTO(asset.getBrand()))
                .status(assetStatusMapper.toDTO(asset.getStatus()))
                .assetSeries(asset.getAssetSeries())
                .plateNumber(asset.getPlateNumber())
                .model(assetModelMapper.toDTO(asset.getAssetModel()))
                .currency(currencyMapper.toDTO(asset.getCurrency()))
                .locationNumber(locationNumberMapper.toDTO(asset.getLocationNumber()))
                .build();
    }

    // PRIVATE METHODS

    private AssetStatus getAssetStatusByName(String name) {
        return switch (name.toLowerCase()) {
            case "in maintenance" -> assetStatusService.findByName(EAssetStatus.ASSET_STATUS_IN_MAINTENANCE);
            case "loaned" -> assetStatusService.findByName(EAssetStatus.ASSET_STATUS_LOANED);
            case "out of service" -> assetStatusService.findByName(EAssetStatus.ASSET_STATUS_OUT_OF_SERVICE);
            case "earring" -> assetStatusService.findByName(EAssetStatus.ASSET_STATUS_EARRING);
            default -> assetStatusService.findByName(EAssetStatus.ASSET_STATUS_AVAILABLE);

        };
    }

    private Currency getCurrencyByName(String name) {
        return switch (name.toLowerCase()) {
            case "usd", "dollar" -> currencyService.findByCurrencyName(ECurrency.CURRENCY_DOLLAR);
            default -> currencyService.findByCurrencyName(ECurrency.CURRENCY_COLON);
        };
    }
}
