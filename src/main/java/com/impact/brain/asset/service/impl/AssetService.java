package com.impact.brain.asset.service.impl;

import com.impact.brain.asset.dto.AssetDTO;
import com.impact.brain.asset.dto.AssetSubcategoryDTO;
import com.impact.brain.asset.dto.LocationNumberDTO;
import com.impact.brain.asset.dto.NumberAndTypeLocationDTO;
import com.impact.brain.asset.entity.*;
import com.impact.brain.asset.repository.*;
import com.impact.brain.asset.service.IAssetService;
import com.impact.brain.brand.entity.Brand;
import com.impact.brain.brand.repository.BrandRepository;
import com.impact.brain.exception.ResourceNotFoundException;
import com.impact.brain.supplier.entity.Supplier;
import com.impact.brain.supplier.service.impl.SupplierService;
import com.impact.brain.user.entity.User;
import com.impact.brain.user.service.impl.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    private final LocationNumberRepository locationNumberRepository;
    private final LocationTypeRepository locationTypeRepository;

    public AssetService(AssetCategoryRepository assetCategoryRepository,
                        AssetRepository assetRepository,
                        AssetStatusRepository assetStatusRepository,
                        SupplierService supplierService,
                        BrandRepository brandRepository,
                        UserService userService,
                        AssetModelRepository assetModelRepository,
                        AssetSubcategoryRepository assetSubcategoryRepository,
                        CurrencyRepository assetCurrencyRepository,
                        LocationNumberRepository locationNumberRepository,
                        LocationTypeRepository locationTypeRepository) {
        this.assetCategoryRepository = assetCategoryRepository;
        this.assetRepository = assetRepository;
        this.assetStatusRepository = assetStatusRepository;
        this.supplierService = supplierService;
        this.brandRepository = brandRepository;
        this.userService = userService;
        this.assetModelRepository = assetModelRepository;
        this.assetSubcategoryRepository = assetSubcategoryRepository;
        this.assetCurrencyRepository = assetCurrencyRepository;
        this.locationNumberRepository = locationNumberRepository;
        this.locationTypeRepository = locationTypeRepository;
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
    public Iterable<Currency> allCurrency() {
        return assetCurrencyRepository.findAll();
    }

    @Override
    public Iterable<AssetSubcategoryDTO> allAssetSubcategory() {
        return assetSubcategoryRepository.findAll()
                .stream()
                .map(this::toDTO) // Convierte cada entidad en un DTO
                .collect(Collectors.toList());      // Recoge los DTOs en una lista
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
        Supplier supplier = supplierService.getById(dto.getSupplierId());
        asset.setSupplier(supplier);

        // Set Brand
        Optional<Brand> brandOptional = brandRepository.findById(dto.getBrandId());
        brandOptional.ifPresent(asset::setBrand);

        // Set Category
        AssetSubcategory assetSubcategory = assetSubcategoryRepository.findById(dto.getSubcategoryId()).orElse(null);
        asset.setSubcategory(assetSubcategory);

        // Set Responsible User
        User responsible = userService.findById(dto.getResponsibleId());
        asset.setResponsible(responsible);

        // Set Status
        AssetStatus status = assetStatusRepository.findById(dto.getStatusId()).orElse(null);
        asset.setStatus(status);

        // Set IsDeleted
        asset.setIsDeleted(dto.getIsDeleted() != null ? dto.getIsDeleted() : false);

        // Set Currency (buscando por el nombre)
        Currency currency = assetCurrencyRepository.findById(dto.getCurrencyId()).get() ;
        asset.setCurrency(currency);

        // Set AssetModel (buscando por el nombre)
        AssetModel assetModel = assetModelRepository.findById(dto.getAssetModelId()).get();
        asset.setAssetModel(assetModel);

        // Asignar valores adicionales
        asset.setAssetSeries(dto.getAssetSeries());
        asset.setPlateNumber(dto.getPlateNumber());

        asset.setLocationNumber(locationNumberRepository.findById(dto.getLocationNumber()).get());

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

    @Override
    public AssetCategory saveCategory(AssetCategory category) {

        return assetCategoryRepository.save(category);
    }
    @Override
    public AssetSubcategory saveSubcategory(AssetSubcategoryDTO assetSubcategoryDTO) {
        System.out.println(assetSubcategoryDTO);
        return assetSubcategoryRepository.save(toEntity(assetSubcategoryDTO));
    }
    @Override
    public AssetModel saveModel(AssetModel assetModel) {
        return assetModelRepository.save(assetModel);
    }

    @Override
    public LocationNumber saveLocationNumber(LocationNumberDTO locationNumberDTO) {
        return locationNumberRepository.save(toEntity(locationNumberDTO));
    }

    @Override
    public LocationType saveLocationType(LocationType locationType) {
        return locationTypeRepository.save(locationType);
    }

    public  LocationNumber toEntity(LocationNumberDTO locationNumberDTO){
        LocationNumber locationNumber = new LocationNumber();
        locationNumber.setId(locationNumberDTO.getId());
        locationNumber.setLocationNumber(locationNumberDTO.getLocationNumber());
        locationNumber.setLocationType((LocationType) locationTypeRepository.findById(locationNumberDTO.getLocationType()).get());
        return locationNumber;
    }

    public Asset getById(Integer id) {
        return assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with ID: " + id));
    }

    // Método para convertir de DTO a Entidad
    public AssetSubcategory toEntity(AssetSubcategoryDTO dto) {
        System.out.println("ing to entity");
        AssetSubcategory subcategory = new AssetSubcategory();
        subcategory.setId(dto.getId());
        subcategory.setName(dto.getName());
        subcategory.setDescription(dto.getDescription());
        subcategory.setCategory(assetCategoryRepository.findById(dto.getCategoryId()).get());
        return subcategory;
    }

    // Método para convertir de Entidad a DTO
    public AssetSubcategoryDTO toDTO(AssetSubcategory entity) {
        AssetSubcategoryDTO dto =  new AssetSubcategoryDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setCategoryId(entity.getCategory().getId());
        return dto;
    }
    public Iterable<LocationType> getAllLocationType(){
        return locationTypeRepository.findAll();
    }
    public List<NumberAndTypeLocationDTO> getAllLocationNumber(){
        List<NumberAndTypeLocationDTO> numberAndTypeLocationDTOS = new ArrayList<>();
        // Obtenemos todos los números y tipos de localización desde los repositorios
        Iterable<LocationNumber> locationNumbers = locationNumberRepository.findAll();
        for (LocationNumber locationNumber : locationNumbers) {
            NumberAndTypeLocationDTO dto = new NumberAndTypeLocationDTO();
            dto.setId(locationNumber.getId());
            dto.setLocationNumber(locationNumber.getLocationNumber());
            dto.setLocationType(locationNumber.getLocationType().getTypeName());
            numberAndTypeLocationDTOS.add(dto);
        }
        return numberAndTypeLocationDTOS;
    }
}
