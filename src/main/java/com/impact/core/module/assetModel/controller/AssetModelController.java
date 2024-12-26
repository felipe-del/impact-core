package com.impact.core.module.assetModel.controller;

import com.impact.core.module.assetModel.payload.request.AssetModelRequest;
import com.impact.core.module.assetModel.payload.response.AssetModelResponse;
import com.impact.core.module.assetModel.service.AssetModelService;
import com.impact.core.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asset-model")
@RequiredArgsConstructor
public class AssetModelController {
    public final AssetModelService assetModelService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<List<AssetModelResponse>>> getAllAssetModels() {
        List<AssetModelResponse> assetModelResponses = assetModelService.findAll();

        return ResponseEntity.ok(ApiResponse.<List<AssetModelResponse>>builder()
                .message("Lista de modelos de activo.")
                .data(assetModelResponses)
                .build());
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<AssetModelResponse>> saveAssetModel(@Valid @RequestBody AssetModelRequest assetModelRequest) {
        AssetModelResponse modelResponse = assetModelService.save(assetModelRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<AssetModelResponse>builder()
                .message("Modelo de activo guardado.")
                .data(modelResponse)
                .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<AssetModelResponse>> updateAssetModel(@PathVariable int id, @Valid @RequestBody AssetModelRequest assetModelRequest) {
        AssetModelResponse modelResponse = assetModelService.update(id, assetModelRequest);

        return ResponseEntity.ok(ApiResponse.<AssetModelResponse>builder()
                .message("Modelo de activo actualizado.")
                .data(modelResponse)
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<AssetModelResponse>> deleteAssetModel(@PathVariable int id) {
        AssetModelResponse modelResponse = assetModelService.delete(id);

        return ResponseEntity.ok(ApiResponse.<AssetModelResponse>builder()
                .message("Modelo de activo eliminado.")
                .data(modelResponse)
                .build());
    }
}
