package com.impact.brain.asset.service.impl;

import com.impact.brain.asset.dto.AssetDTO;
import com.impact.brain.asset.entity.Asset;
import com.impact.brain.asset.entity.AssetCategory;
import com.impact.brain.asset.entity.AssetStatus;
import com.impact.brain.asset.repository.AssetCategoryRepository;
import com.impact.brain.asset.repository.AssetRepository;
import com.impact.brain.asset.repository.AssetStatusRepository;
import com.impact.brain.asset.service.IAssetService;
import com.impact.brain.brand.entity.Brand;
import com.impact.brain.brand.repository.BrandRepository;
import com.impact.brain.supplier.entity.Supplier;
import com.impact.brain.supplier.service.impl.SupplierService;
import com.impact.brain.user.service.impl.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Isaac F. B. C.
 * @since 9/14/2024 - 6:21 AM
 */
@Service
public class AssetService implements IAssetService {

    private final AssetCategoryRepository assetCategoryRepository;
    private final AssetRepository assetRepository;
    private final AssetStatusRepository assetStatusRepository;
    private final SupplierService supplierService;
    private final BrandRepository brandRepository;
    private final UserService userService;

    public AssetService(AssetCategoryRepository assetCategoryRepository,
                        AssetRepository assetRepository,
                        AssetStatusRepository assetStatusRepository,
                        SupplierService supplierService, BrandRepository brandRepository, UserService userService) {
        this.assetCategoryRepository = assetCategoryRepository;
        this.assetRepository = assetRepository;
        this.assetStatusRepository = assetStatusRepository;
        this.supplierService = supplierService;
        this.brandRepository = brandRepository;
        this.userService = userService;
    }

    @Override
    public Iterable<Asset> all() {
        return assetRepository.findAll();
    }

    @Override
    public Iterable<AssetStatus> allStatus() {
        return assetStatusRepository.findAll();
    }

    @Override
    public Iterable<AssetCategory> allCategories() {
        return assetCategoryRepository.findAll();
    }

    @Override
    public Asset findById(int id) {
        return assetRepository.findById(id).orElse(null);
    }

    @Override
    public Asset mapper_DTOtoEntity(AssetDTO dto) {
        Asset asset = new Asset();
        asset.setId(dto.getId());
        asset.setPurchaseDate(dto.getPurchaseDate());
        asset.setValue(dto.getValue());
        Optional<Supplier> supplierOptional = supplierService.getById(dto.getSupplierId());
        supplierOptional.ifPresent(asset::setSupplier);
        Optional<Brand> brandOptional = brandRepository.findById(dto.getBrandId());
        brandOptional.ifPresent(asset::setBrand);
        asset.setIsDeleted(dto.getIsDeleted());
        asset.setCategory(assetCategoryRepository.findById(dto.getCategoryId()).orElse(null));
        asset.setResponsible(userService.findById(dto.getResponsibleId()));
        return asset;
    }

    @Override
    public AssetCategory findCategoryById(int id) {
        return assetCategoryRepository.findById(id);
    }

    @Override
    public Asset save(AssetDTO dto) {
        return assetRepository.save(this.mapper_DTOtoEntity(dto));
    }
}
