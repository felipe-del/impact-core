package com.impact.brain.asset.controller;

import com.impact.brain.asset.dto.AssetDTO;
import com.impact.brain.asset.dto.AssetListDTO;
import com.impact.brain.asset.entity.Asset;
import com.impact.brain.asset.entity.AssetCategory;
import com.impact.brain.asset.entity.AssetStatus;
import com.impact.brain.asset.service.impl.AssetService;
import com.impact.brain.products.dto.ProductCategoryDTO;
import com.impact.brain.products.entity.CategoryType;
import com.impact.brain.products.entity.ProductCategory;
import com.impact.brain.products.entity.UnitOfMeasurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

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

    @PostMapping()
    public void create(@RequestBody AssetDTO assetDTO){
        System.out.println(assetDTO.toString());
        assetService.save(assetDTO);
    }

    @PostMapping("/category")
    public AssetCategory createCategory(@RequestBody AssetCategory assetCategory){
       return assetService.saveCategory(assetCategory);
    }

    @GetMapping("/all")
    public Iterable<AssetListDTO> getList(){
        // Obtener todas las categorías
        Iterable<Asset> assets = assetService.all();
        // Crear una lista para almacenar los DTOs
        ArrayList<AssetListDTO> assetsDTO = new ArrayList<>();

        // Recorrer cada categoría para calcular el count y crear el DTO
        for (Asset asset : assets) {
            // Crear el DTO con los datos necesarios
            AssetListDTO dto = new AssetListDTO();
            dto.setId(asset.getId());
            dto.setCategory(asset.getCategory().getName());
            dto.setNumber(asset.getValue());
            dto.setStatus(asset.getStatus().getName());
            assetsDTO.add(dto);
        }

        // Retornar los DTOs al frontend
        return assetsDTO;
    }
}
