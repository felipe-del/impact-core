package com.impact.brain.asset.controller;

import com.impact.brain.asset.assetRequest.dto.AssetRequestDTO;
import com.impact.brain.asset.assetRequest.entity.AssetRequest;
import com.impact.brain.asset.assetRequest.service.implement.AssetRequestService;
import com.impact.brain.asset.dto.*;
import com.impact.brain.asset.entity.*;
import com.impact.brain.asset.service.impl.AssetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Isaac F. B. C.
 * @since 9/14/2024 - 6:19 AM
 */
@RestController
@RequestMapping("/asset")
public class AssetController {

    private final AssetService assetService;
    private final AssetRequestService assetRequestService;

    public AssetController(AssetService assetService,
                           AssetRequestService assetRequestService) {
        this.assetService = assetService;
        this.assetRequestService = assetRequestService;
    }

    /**
     * Retrieves all assets.
     *
     * @return ResponseEntity containing an iterable of AssetDTO objects.
     */
    @GetMapping
    public ResponseEntity<Iterable<AssetDTO>> getAssets() {
        return ResponseEntity.ok(assetService.all());
    }

    /**
     * Retrieves all asset statuses.
     *
     * @return ResponseEntity containing an iterable of AssetStatus objects.
     */
    @GetMapping("/status")
    public ResponseEntity<Iterable<AssetStatus>> getAssetStatus() {
        return ResponseEntity.ok(assetService.allStatus());
    }

    /**
     * Retrieves all asset categories.
     *
     * @return ResponseEntity containing an iterable of AssetCategory objects.
     */
    @GetMapping("/category")
    public ResponseEntity<Iterable<AssetCategory>> getAssetCategory() {
        return ResponseEntity.ok(assetService.allCategories());
    }

    /**
     * Retrieves all currencies.
     *
     * @return ResponseEntity containing an iterable of Currency objects.
     */
    @GetMapping("/currency")
    public ResponseEntity<Iterable<Currency>> getCurrency() {
        return ResponseEntity.ok(assetService.allCurrency());
    }

    /**
     * Retrieves all asset subcategories.
     *
     * @return ResponseEntity containing an iterable of AssetSubcategoryDTO objects.
     */
    @GetMapping("/subcategory")
    public ResponseEntity<Iterable<AssetSubcategoryDTO>> getSubcategory() {
        return ResponseEntity.ok(assetService.allAssetSubcategory());
    }

    /**
     * Retrieves all asset models.
     *
     * @return ResponseEntity containing an iterable of AssetModel objects.
     */
    @GetMapping("/model")
    public ResponseEntity<Iterable<AssetModel>> getAssetModel() {
        return ResponseEntity.ok(assetService.allAssetModel());
    }

    /**
     * Creates a new asset.
     *
     * @param assetDTO the AssetDTO object to be created.
     * @return ResponseEntity indicating the result of the creation operation.
     */
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody AssetDTO assetDTO) {
        System.out.println(assetDTO.toString());
        assetService.save(assetDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build(); // 201 Created
    }

    /**
     * Creates a new asset category.
     *
     * @param assetDTO the AssetCategory object to be created.
     * @return ResponseEntity containing the created AssetCategory object.
     */
    @PostMapping("/category")
    public ResponseEntity<AssetCategory> createCategory(@RequestBody AssetCategory assetDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assetService.saveCategory(assetDTO));
    }

    /**
     * Creates a new asset subcategory.
     *
     * @param assetSubcategoryDTO the AssetSubcategoryDTO object to be created.
     * @return ResponseEntity containing the created AssetSubcategory object.
     */
    @PostMapping("/subcategory")
    public ResponseEntity<AssetSubcategory> createSubcategory(@RequestBody AssetSubcategoryDTO assetSubcategoryDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assetService.saveSubcategory(assetSubcategoryDTO));
    }

    /**
     * Creates a new asset model.
     *
     * @param assetModel the AssetModel object to be created.
     * @return ResponseEntity containing the created AssetModel object.
     */
    @PostMapping("/model")
    public ResponseEntity<AssetModel> createModel(@RequestBody AssetModel assetModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assetService.saveModel(assetModel));
    }

    /**
     * Creates a new location number.
     *
     * @param locationNumberDTO the LocationNumberDTO object to be created.
     * @return ResponseEntity containing the created LocationNumber object.
     */
    @PostMapping("/locationNumber")
    public ResponseEntity<LocationNumber> createLocationNumber(@RequestBody LocationNumberDTO locationNumberDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assetService.saveLocationNumber(locationNumberDTO));
    }

    /**
     * Creates a new location type.
     *
     * @param locationType the LocationType object to be created.
     * @return ResponseEntity containing the created LocationType object.
     */
    @PostMapping("/locationType")
    public ResponseEntity<LocationType> createLocationType(@RequestBody LocationType locationType) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assetService.saveLocationType(locationType));
    }

    /**
     * Retrieves all location types.
     *
     * @return ResponseEntity containing an iterable of LocationType objects.
     */
    @GetMapping("/locationType")
    public ResponseEntity<Iterable<LocationType>> getAll() {
        return ResponseEntity.ok(assetService.getAllLocationType());
    }

    /**
     * Retrieves all location numbers.
     *
     * @return ResponseEntity containing a list of NumberAndTypeLocationDTO objects.
     */
    @GetMapping("/locationNumber")
    public ResponseEntity<List<NumberAndTypeLocationDTO>> getAllLocationNumber() {
        return ResponseEntity.ok(assetService.getAllLocationNumber());
    }

    /**
     * Retrieves a list of all assets with details.
     *
     * @return ResponseEntity containing an iterable of AssetListDTO objects.
     */
    @GetMapping("/all")
    public ResponseEntity<Iterable<AssetListDTO>> getList() {
        // Obtener todas las categorías
        Iterable<Asset> assets = assetService.allAsset();
        // Crear una lista para almacenar los DTOs
        ArrayList<AssetListDTO> assetsDTO = new ArrayList<>();

        // Recorrer cada categoría para calcular el count y crear el DTO
        for (Asset asset : assets) {
            // Crear el DTO con los datos necesarios
            AssetListDTO dto = new AssetListDTO();
            dto.setId(asset.getId());
            dto.setCategory(asset.getSubcategory().getCategory().getName());
            dto.setSubcategory(asset.getSubcategory().getName());
            dto.setDescription(asset.getSubcategory().getDescription());
            dto.setPlate(asset.getPlateNumber());
            dto.setLocation(asset.getLocationNumber().getLocationType().getTypeName());
            dto.setStatus(asset.getStatus().getName());
            assetsDTO.add(dto);
        }

        // Retornar los DTOs al frontend
        return ResponseEntity.ok(assetsDTO);
    }

    /**
     * Creates a new asset request.
     *
     * @param assetRequestDTO the AssetRequestDTO object to be created.
     * @return ResponseEntity containing the created AssetRequestDTO object.
     */
    @PostMapping("/request")
    public ResponseEntity<AssetRequestDTO> createAssetRequest(@RequestBody AssetRequestDTO assetRequestDTO) {
        System.out.println(assetRequestDTO.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(assetRequestService.save(assetRequestDTO));
    }
}