package com.impact.core.module.asset.controller;

import com.impact.core.module.asset.payload.request.AssetRequest;
import com.impact.core.module.asset.payload.response.AssetResponse;
import com.impact.core.module.asset.service.AssetService;
import com.impact.core.module.currency.payload.response.SumOfCurrency;
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

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<AssetResponse>> getAsset(@PathVariable int id) {
        AssetResponse assetResponse = assetService.findByIdR(id);

        return ResponseEntity.ok(ResponseWrapper.<AssetResponse>builder()
                .message("Activo encontrado.")
                .data(assetResponse)
                .build());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
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
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<List<SumOfCurrency>>> getInventoryValue(
            @RequestParam("start_date") LocalDate start_date,
            @RequestParam("end_date") LocalDate end_date) {
        List<SumOfCurrency> inventoryValues = assetService.getInventoryValue(start_date, end_date);

        return ResponseEntity.ok(ResponseWrapper.<List<SumOfCurrency>>builder()
                .message("Valor del inventario")
                .data(inventoryValues)
                .build());
    }
    @PutMapping("/{statusId}/{assetId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<Void>> updateStatus(@PathVariable Integer statusId, @PathVariable String assetId){
        assetService.updateStatus(statusId,assetId);

        return ResponseEntity.ok(ResponseWrapper.<Void>builder()
                .message("Cambio de estado de activo a DISPONIBLE.")
                .build());
    }

    @PutMapping("/assetRequestAccept/{assetId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') ")
    public ResponseEntity<ResponseWrapper<Void>> updateStatusAccepted( @PathVariable String assetId){
        assetService.updateStatus(3,assetId); //status 3: ASSET_STATUS_LOANED

        return ResponseEntity.ok(ResponseWrapper.<Void>builder()
                .message("Cambio de estado de activo a PRESTADO.")
                .build());
    }
}
