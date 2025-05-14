package com.impact.core.module.assetSubcategory.controller;

import com.impact.core.module.assetSubcategory.entity.AssetSubcategory;
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

/**
 * REST controller responsible for managing {@link AssetSubcategory} resources.
 * <p>
 * This controller provides endpoints for creating, updating, deleting, and retrieving {@link AssetSubcategory} entities.
 * It is protected by role-based access control and requires either the 'ADMINISTRATOR' or 'MANAGER' role for all operations.
 * </p>
 */
@RestController
@RequestMapping("/api/asset-sub-category")
@RequiredArgsConstructor
public class AssetSubcategoryController {
    public final AssetSubcategoryService assetSubcategoryService;

    /**
     * Retrieves all {@link AssetSubcategory} entities.
     *
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper} with a list of {@link AssetSubcategoryResponse} DTOs
     *         representing all {@link AssetSubcategory} entities.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<AssetSubcategoryResponse>>> getAllAssetSubcategories() {
        List<AssetSubcategoryResponse> assetSubcategoryResponses = assetSubcategoryService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<AssetSubcategoryResponse>>builder()
                .message("Lista de subcategorías de activo.")
                .data(assetSubcategoryResponses)
                .build());
    }

    /**
     * Creates a new {@link AssetSubcategory} entity.
     *
     * @param assetSubcategoryRequest the {@link AssetSubcategoryRequest} containing the data to create the {@link AssetSubcategory}
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper} with the {@link AssetSubcategoryResponse} of the
     *         created {@link AssetSubcategory}.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<AssetSubcategoryResponse>> saveAssetSubcategory(@Valid @RequestBody AssetSubcategoryRequest assetSubcategoryRequest) {
        AssetSubcategoryResponse subcategoryResponse = assetSubcategoryService.save(assetSubcategoryRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.<AssetSubcategoryResponse>builder()
                .message("Subcategoría de activo guardada.")
                .data(subcategoryResponse)
                .build());
    }

    /**
     * Updates an existing {@link AssetSubcategory} entity.
     *
     * @param id the ID of the {@link AssetSubcategory} to update
     * @param assetSubcategoryRequest the {@link AssetSubcategoryRequest} containing the updated data
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper} with the {@link AssetSubcategoryResponse} of the
     *         updated {@link AssetSubcategory}.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<AssetSubcategoryResponse>> updateAssetSubcategory(@PathVariable int id, @Valid @RequestBody AssetSubcategoryRequest assetSubcategoryRequest) {
        AssetSubcategoryResponse subcategoryResponse = assetSubcategoryService.update(id, assetSubcategoryRequest);

        return ResponseEntity.ok(ResponseWrapper.<AssetSubcategoryResponse>builder()
                .message("Subcategoría de activo actualizada.")
                .data(subcategoryResponse)
                .build());
    }

    /**
     * Deletes an existing {@link AssetSubcategory} entity.
     *
     * @param id the ID of the {@link AssetSubcategory} to delete
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper} with the {@link AssetSubcategoryResponse} of the
     *         deleted {@link AssetSubcategory}.
     */
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
