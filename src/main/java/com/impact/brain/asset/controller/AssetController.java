package com.impact.brain.asset.controller;

import com.impact.brain.asset.dto.AssetDTO;
import com.impact.brain.asset.dto.AssetListDTO;
import com.impact.brain.asset.dto.AssetSubcategoryDTO;
import com.impact.brain.asset.entity.*;
import com.impact.brain.asset.service.impl.AssetService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * @author Isaac F. B. C.
 * @since 9/14/2024 - 6:19 AM
 */
@RestController
@RequestMapping("/asset")
public class AssetController {
    final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping
    public Iterable<Asset> getAssets() {
        return assetService.all();
    }

    @GetMapping("/status")
    public Iterable<AssetStatus> getAssetStatus() {
        return assetService.allStatus();
    }

    @GetMapping("/category")
    public Iterable<AssetCategory> getAssetCategory() {
        return assetService.allCategories();
    }

    @GetMapping("/currency")
    public Iterable<Currency> getCurrency() {
        return assetService.allCurrency();
    }

    @GetMapping("/subcategory")
    public Iterable<AssetSubcategoryDTO> getSubcategory() {
        return assetService.allAssetSubcategory();
    }

    @GetMapping("/model")
    public Iterable<AssetModel> getAssetModel() {
        return assetService.allAssetModel();
    }

    @PostMapping()
    public void create(@RequestBody AssetDTO assetDTO) {
        System.out.println(assetDTO.toString());
        assetService.save(assetDTO);
    }

    @PostMapping("/category")
    public AssetCategory createCategory(@RequestBody AssetCategory assetDTO) {
        return assetService.saveCategory(assetDTO);
    }

    @PostMapping("/subcategory")
    public AssetSubcategory createSubcategory(@RequestBody AssetSubcategoryDTO assetSubcategoryDTO) {
        return assetService.saveSubcategory(assetSubcategoryDTO);
    }

    @PostMapping("/model")
    public AssetModel createModel(@RequestBody AssetModel assetModel) {
        return assetService.saveModel(assetModel);
    }

    @GetMapping("/all")
    public Iterable<AssetListDTO> getList() {
        // Obtener todas las categorías
        Iterable<Asset> assets = assetService.all();
        // Crear una lista para almacenar los DTOs
        ArrayList<AssetListDTO> assetsDTO = new ArrayList<>();

        // Recorrer cada categoría para calcular el count y crear el DTO
        for (Asset asset : assets) {
            // Crear el DTO con los datos necesarios
            AssetListDTO dto = new AssetListDTO();
            dto.setId(asset.getId());
            dto.setPlate(asset.getPlateNumber());
            dto.setCategory(asset.getSubcategory().getName());
            dto.setSubcategory(asset.getSubcategory().getName());
            dto.setStatus(asset.getStatus().getName());
            assetsDTO.add(dto);
        }

        // Retornar los DTOs al frontend
        return assetsDTO;
    }
}
