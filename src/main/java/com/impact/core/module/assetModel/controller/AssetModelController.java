package com.impact.core.module.assetModel.controller;

import com.impact.core.module.assetModel.payload.request.AssetModelRequest;
import com.impact.core.module.assetModel.payload.response.AssetModelResponse;
import com.impact.core.module.assetModel.service.AssetModelService;
import com.impact.core.util.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing asset models.
 * <p>
 * This controller provides endpoints to handle CRUD operations for asset models, including retrieving,
 * saving, updating, and deleting asset models. The access to these endpoints is restricted to users with
 * roles 'ADMINISTRATOR' or 'MANAGER'.
 * </p>
 */
@RestController
@RequestMapping("/api/asset-model")
@RequiredArgsConstructor
public class AssetModelController {
    public final AssetModelService assetModelService;

    /**
     * Get a list of all asset models in the system.
     * <p>
     * Only users with the 'ADMINISTRATOR' or 'MANAGER' role are authorized to access this endpoint.
     * </p>
     *
     * @return {@link ResponseEntity} with the list of {@link AssetModelResponse} objects.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<AssetModelResponse>>> getAllAssetModels() {
        List<AssetModelResponse> assetModelResponses = assetModelService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<AssetModelResponse>>builder()
                .message("Lista de modelos de activo.")
                .data(assetModelResponses)
                .build());
    }

    /**
     * Save a new asset model to the system.
     * <p>
     * Only users with the 'ADMINISTRATOR' or 'MANAGER' role are authorized to access this endpoint.
     * </p>
     *
     * @param assetModelRequest The request payload containing the asset model details.
     * @return {@link ResponseEntity} containing the saved {@link AssetModelResponse}.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<AssetModelResponse>> saveAssetModel(@Valid @RequestBody AssetModelRequest assetModelRequest) {
        AssetModelResponse modelResponse = assetModelService.save(assetModelRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.<AssetModelResponse>builder()
                .message("Modelo de activo guardado.")
                .data(modelResponse)
                .build());
    }

    /**
     * Update an existing asset model in the system.
     * <p>
     * Only users with the 'ADMINISTRATOR' or 'MANAGER' role are authorized to access this endpoint.
     * </p>
     *
     * @param id The ID of the asset model to update.
     * @param assetModelRequest The request payload containing the updated asset model details.
     * @return {@link ResponseEntity} containing the updated {@link AssetModelResponse}.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<AssetModelResponse>> updateAssetModel(@PathVariable int id, @Valid @RequestBody AssetModelRequest assetModelRequest) {
        AssetModelResponse modelResponse = assetModelService.update(id, assetModelRequest);

        return ResponseEntity.ok(ResponseWrapper.<AssetModelResponse>builder()
                .message("Modelo de activo actualizado.")
                .data(modelResponse)
                .build());
    }

    /**
     * Delete an asset model from the system.
     * <p>
     * Only users with the 'ADMINISTRATOR' or 'MANAGER' role are authorized to access this endpoint.
     * </p>
     *
     * @param id The ID of the asset model to delete.
     * @return {@link ResponseEntity} confirming the deletion of the asset model.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<AssetModelResponse>> deleteAssetModel(@PathVariable int id) {
        AssetModelResponse modelResponse = assetModelService.delete(id);

        return ResponseEntity.ok(ResponseWrapper.<AssetModelResponse>builder()
                .message("Modelo de activo eliminado.")
                .data(modelResponse)
                .build());
    }
}
