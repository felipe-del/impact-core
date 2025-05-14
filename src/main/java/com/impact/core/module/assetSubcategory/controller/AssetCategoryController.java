package com.impact.core.module.assetSubcategory.controller;

import com.impact.core.module.assetSubcategory.entity.AssetCategory;
import com.impact.core.module.assetSubcategory.payload.request.AssetCategoryRequest;
import com.impact.core.module.assetSubcategory.payload.response.AssetCategoryResponse;
import com.impact.core.module.assetSubcategory.service.AssetCategoryService;
import com.impact.core.util.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller responsible for managing {@link AssetCategory} resources.
 * <p>
 * This controller provides endpoints for creating, updating, deleting, and retrieving {@link AssetCategory} entities.
 * It is protected by role-based access control and requires either the 'ADMINISTRATOR' or 'MANAGER' role for all operations.
 * </p>
 */
@RestController
@RequestMapping("/api/asset-category")
@RequiredArgsConstructor
public class AssetCategoryController {
    public final AssetCategoryService assetCategoryService;

    /**
     * Retrieves all {@link AssetCategory} entities.
     *
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper} with a list of {@link AssetCategoryResponse} DTOs
     *         representing all {@link AssetCategory} entities.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<AssetCategoryResponse>>> getAllAssetCategories() {
        List<AssetCategoryResponse> assetCategoryResponses = assetCategoryService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<AssetCategoryResponse>>builder()
                .message("Lista de categorías de activo.")
                .data(assetCategoryResponses)
                .build());
    }

    /**
     * Creates a new {@link AssetCategory} entity.
     *
     * @param assetCategoryRequest the {@link AssetCategoryRequest} containing the data to create the {@link AssetCategory}
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper} with the {@link AssetCategoryResponse} of the
     *         created {@link AssetCategory}.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<AssetCategoryResponse>> saveAssetCategory(@Valid @RequestBody AssetCategoryRequest assetCategoryRequest) {
        AssetCategoryResponse categoryResponse = assetCategoryService.save(assetCategoryRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.<AssetCategoryResponse>builder()
                .message("Categoria de activo guardada.")
                .data(categoryResponse)
                .build());
    }

    /**
     * Updates an existing {@link AssetCategory} entity.
     *
     * @param id the ID of the {@link AssetCategory} to update
     * @param assetCategoryRequest the {@link AssetCategoryRequest} containing the updated data
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper} with the {@link AssetCategoryResponse} of the
     *         updated {@link AssetCategory}.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<AssetCategoryResponse>> updateAssetCategory(@PathVariable int id, @Valid @RequestBody AssetCategoryRequest assetCategoryRequest) {
        AssetCategoryResponse categoryResponse = assetCategoryService.update(id, assetCategoryRequest);

        return ResponseEntity.ok(ResponseWrapper.<AssetCategoryResponse>builder()
                .message("Categoria de activo actualizada.")
                .data(categoryResponse)
                .build());
    }

    /**
     * Deletes an existing {@link AssetCategory} entity.
     *
     * @param id the ID of the {@link AssetCategory} to delete
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper} with the {@link AssetCategoryResponse} of the
     *         deleted {@link AssetCategory}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<AssetCategoryResponse>> deleteAssetCategory(@PathVariable int id) {
        AssetCategoryResponse categoryResponse = assetCategoryService.delete(id);

        return ResponseEntity.ok(ResponseWrapper.<AssetCategoryResponse>builder()
                .message("Categoria de activo eliminada.")
                .data(categoryResponse)
                .build());
    }
}
