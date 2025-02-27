package com.impact.core.module.assetSubcategory.controller;

import com.impact.core.module.assetSubcategory.payload.request.AssetSubcategoryRequest;
import com.impact.core.module.assetSubcategory.payload.response.AssetSubcategoryResponse;
import com.impact.core.module.assetSubcategory.service.AssetSubcategoryService;
import com.impact.core.util.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asset-sub-category")
@RequiredArgsConstructor
public class AssetSubcategoryController {
    public final AssetSubcategoryService assetSubcategoryService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<AssetSubcategoryResponse>>> getAllAssetSubcategories() {
        List<AssetSubcategoryResponse> assetSubcategoryResponses = assetSubcategoryService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<AssetSubcategoryResponse>>builder()
                .message("Lista de subcategorías de activo.")
                .data(assetSubcategoryResponses)
                .build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<AssetSubcategoryResponse>> saveAssetSubcategory(@Valid @RequestBody AssetSubcategoryRequest assetSubcategoryRequest) {
        AssetSubcategoryResponse subcategoryResponse = assetSubcategoryService.save(assetSubcategoryRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.<AssetSubcategoryResponse>builder()
                .message("Subcategoría de activo guardada.")
                .data(subcategoryResponse)
                .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<AssetSubcategoryResponse>> updateAssetSubcategory(@PathVariable int id, @Valid @RequestBody AssetSubcategoryRequest assetSubcategoryRequest) {
        AssetSubcategoryResponse subcategoryResponse = assetSubcategoryService.update(id, assetSubcategoryRequest);

        return ResponseEntity.ok(ResponseWrapper.<AssetSubcategoryResponse>builder()
                .message("Subcategoría de activo actualizada.")
                .data(subcategoryResponse)
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<AssetSubcategoryResponse>> deleteAssetSubcategory(@PathVariable int id) {
        AssetSubcategoryResponse subcategoryResponse = assetSubcategoryService.delete(id);

        return ResponseEntity.ok(ResponseWrapper.<AssetSubcategoryResponse>builder()
                .message("Subcategoría de activo eliminada.")
                .data(subcategoryResponse)
                .build());
    }
}
