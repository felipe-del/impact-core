package com.impact.brain.asset.service.impl;

import com.impact.brain.asset.dto.AssetCategoryDTO;
import com.impact.brain.asset.dto.AssetDTO;
import com.impact.brain.asset.entity.*;
import com.impact.brain.asset.repository.*;
import com.impact.brain.asset.service.IAssetService;
import com.impact.brain.brand.entity.Brand;
import com.impact.brain.brand.repository.BrandRepository;
import com.impact.brain.supplier.entity.Supplier;
import com.impact.brain.supplier.service.impl.SupplierService;
import com.impact.brain.user.entity.User;
import com.impact.brain.user.service.impl.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    private  final AssetModelRepository assetModelRepository;
    private  final AssetSubcategoryRepository assetSubcategoryRepository;
    private  final CurrencyRepository assetCurrencyRepository;

    public AssetService(AssetCategoryRepository assetCategoryRepository,
                        AssetRepository assetRepository,
                        AssetStatusRepository assetStatusRepository,
                        SupplierService supplierService,
                        BrandRepository brandRepository,
                        UserService userService,
                        AssetModelRepository assetModelRepository,
                        AssetSubcategoryRepository assetSubcategoryRepository,
                        CurrencyRepository assetCurrencyRepository) {
        this.assetCategoryRepository = assetCategoryRepository;
        this.assetRepository = assetRepository;
        this.assetStatusRepository = assetStatusRepository;
        this.supplierService = supplierService;
        this.brandRepository = brandRepository;
        this.userService = userService;
        this.assetModelRepository = assetModelRepository;
        this.assetSubcategoryRepository = assetSubcategoryRepository;
        this.assetCurrencyRepository = assetCurrencyRepository;
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
    public Iterable<AssetCategoryDTO> allCategories() {
        Iterable<AssetCategory> categories = assetCategoryRepository.findAll();
        return StreamSupport.stream(categories.spliterator(), false)
                .map(this::mapEntityToDto) // Map each AssetCategory to AssetCategoryDTO
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Currency> allCurrency() {
        return assetCurrencyRepository.findAll();
    }

    @Override
    public Iterable<AssetSubcategory> allAssetSubcategory() {
        return assetSubcategoryRepository.findAll();
    }

    @Override
    public Iterable<AssetModel> allAssetModel() {
        return assetModelRepository.findAll();
    }

    @Override
    public Asset findById(int id) {
        return assetRepository.findById(id).orElse(null);
    }

    @Override
    public Asset mapper_DTOtoEntity(AssetDTO dto) {
        Asset asset = new Asset();

        // Asignar ID, fecha de compra, y valor
        asset.setId(dto.getId());
        asset.setPurchaseDate(dto.getPurchaseDate());
        asset.setValue(dto.getValue());

        // Set Supplier
        Optional<Supplier> supplierOptional = supplierService.getById(dto.getSupplierId());
        supplierOptional.ifPresent(asset::setSupplier);

        // Set Brand
        Optional<Brand> brandOptional = brandRepository.findById(dto.getBrandId());
        brandOptional.ifPresent(asset::setBrand);

        // Set Category
        AssetCategory category = assetCategoryRepository.findById(dto.getCategoryId()).orElse(null);
        asset.setCategory(category);

        // Set Responsible User
        User responsible = userService.findById(dto.getResponsibleId());
        asset.setResponsible(responsible);

        // Set Status
        AssetStatus status = assetStatusRepository.findById(dto.getStatusId()).orElse(null);
        asset.setStatus(status);

        // Set IsDeleted
        asset.setIsDeleted(dto.getIsDeleted() != null ? dto.getIsDeleted() : false);

        // Set Currency (buscando por el nombre)
        Currency currency = assetCurrencyRepository.findByCurrencyName(dto.getCurrencyName()) ;
        asset.setCurrency(currency);

        // Set AssetModel (buscando por el nombre)
        AssetModel assetModel = assetModelRepository.findByModelName(dto.getAssetModelName());
        asset.setAssetModel(assetModel);

        // Asignar valores adicionales
        asset.setAssetSeries(dto.getAssetSeries());
        asset.setPlateNumber(dto.getPlateNumber());

        return asset;
    }
    @Override
    public AssetCategory mapper_DTOtoAssetCategory(AssetCategoryDTO dto) {
        AssetCategory assetCategory = new AssetCategory();
        assetCategory.setId(dto.getId());
        assetCategory.setName(dto.getName());
        assetCategory.setSubcategory(assetSubcategoryRepository.findById(dto.getSubcategoryId()).orElse(null));
        return assetCategory;
    }

    @Override
    public AssetCategory findCategoryById(int id) {
        return assetCategoryRepository.findById(id);
    }

    @Override
    public Asset save(AssetDTO dto) {
        return assetRepository.save(this.mapper_DTOtoEntity(dto));
    }

    @Override
    public AssetCategory saveCategory(AssetCategory category) {

        return assetCategoryRepository.save(category);
    }
    @Override
    public AssetSubcategory saveSubcategory(AssetSubcategory assetSubcategory) {
        return assetSubcategoryRepository.save(assetSubcategory);
    }
    @Override
    public AssetModel saveModel(AssetModel assetModel) {
        return assetModelRepository.save(assetModel);
    }
    public AssetCategory mapDtoToEntity(AssetCategoryDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("AssetCategoryDTO cannot be null");
        }

        AssetCategory category = new AssetCategory();
        category.setId(dto.getId());
        category.setName(dto.getName());

        // Assuming you have a repository to fetch the subcategory by ID
        AssetSubcategory subcategory = assetSubcategoryRepository.findById(dto.getSubcategoryId())
                .orElseThrow(() -> new RuntimeException("Subcategory not found"));
        category.setSubcategory(subcategory);

        return category;
    }
    public AssetCategoryDTO mapEntityToDto(AssetCategory category) {
        if (category == null) {
            throw new IllegalArgumentException("AssetCategory cannot be null");
        }

        AssetCategoryDTO dto = new AssetCategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setSubcategoryId(category.getSubcategory().getId()); // Get subcategory ID

        return dto;
    }


}
