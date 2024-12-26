package com.impact.core.module.assetSubcategory.controller;

import com.impact.core.module.assetSubcategory.payload.request.AssetCategoryRequest;
import com.impact.core.module.assetSubcategory.payload.response.AssetCategoryResponse;
import com.impact.core.module.assetSubcategory.service.AssetCategoryService;
import com.impact.core.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asset-category")
@RequiredArgsConstructor
public class AssetCategoryController {
    public final AssetCategoryService assetCategoryService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<List<AssetCategoryResponse>>> getAllAssetCategories() {
        List<AssetCategoryResponse> assetCategoryResponses = assetCategoryService.findAll();

        return ResponseEntity.ok(ApiResponse.<List<AssetCategoryResponse>>builder()
                .message("Lista de categorías de activo.")
                .data(assetCategoryResponses)
                .build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<AssetCategoryResponse>> saveAssetCategory(@Valid @RequestBody AssetCategoryRequest assetCategoryRequest) {
        AssetCategoryResponse categoryResponse = assetCategoryService.save(assetCategoryRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<AssetCategoryResponse>builder()
                .message("Categoria de activo guardada.")
                .data(categoryResponse)
                .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<AssetCategoryResponse>> updateAssetCategory(@PathVariable int id, @Valid @RequestBody AssetCategoryRequest assetCategoryRequest) {
        AssetCategoryResponse categoryResponse = assetCategoryService.update(id, assetCategoryRequest);

        return ResponseEntity.ok(ApiResponse.<AssetCategoryResponse>builder()
                .message("Categoria de activo actualizada.")
                .data(categoryResponse)
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<AssetCategoryResponse>> deleteAssetCategory(@PathVariable int id) {
        AssetCategoryResponse categoryResponse = assetCategoryService.delete(id);

        return ResponseEntity.ok(ApiResponse.<AssetCategoryResponse>builder()
                .message("Categoria de activo eliminada.")
                .data(categoryResponse)
                .build());
    }


}
