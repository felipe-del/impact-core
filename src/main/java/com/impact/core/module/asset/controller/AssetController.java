package com.impact.core.module.asset.controller;

import com.impact.core.module.asset.payload.request.AssetRequest;
import com.impact.core.module.asset.payload.response.AssetResponse;
import com.impact.core.module.asset.service.AssetService;
import com.impact.core.util.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/asset")
@RequiredArgsConstructor
public class AssetController {
    public final AssetService assetService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<AssetResponse>>> getAllAssets() {
        List<AssetResponse> assetResponses = assetService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<AssetResponse>>builder()
                .message("Lista de activos.")
                .data(assetResponses)
                .build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<AssetResponse>> saveAsset(@Valid @RequestBody AssetRequest assetRequest) {
        AssetResponse assetResponse = assetService.save(assetRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.<AssetResponse>builder()
                .message("Activo guardado.")
                .data(assetResponse)
                .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<AssetResponse>> updateAsset(@PathVariable int id, @Valid @RequestBody AssetRequest assetRequest) {
        AssetResponse assetResponse = assetService.update(id, assetRequest);

        return ResponseEntity.ok(ResponseWrapper.<AssetResponse>builder()
                .message("Activo actualizado.")
                .data(assetResponse)
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<AssetResponse>> deleteAsset(@PathVariable int id) {
        AssetResponse assetResponse = assetService.delete(id);

        return ResponseEntity.ok(ResponseWrapper.<AssetResponse>builder()
                .message("Activo eliminado.")
                .data(assetResponse)
                .build());
    }

    @GetMapping("/inventory-value")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<Object[]>>> getInventoryValue(
            @RequestParam("start_date") LocalDate start_date,
            @RequestParam("end_date") LocalDate end_date) {
        List<Object[]> inventoryValues = assetService.getInventoryValue(start_date, end_date);

        return ResponseEntity.ok(ResponseWrapper.<List<Object[]>>builder()
                .message("Valor del inventario")
                .data(inventoryValues)
                .build());
    }
}
