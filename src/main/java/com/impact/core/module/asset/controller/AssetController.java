package com.impact.core.module.asset.controller;

import com.impact.core.module.asset.payload.request.AssetRequest;
import com.impact.core.module.asset.payload.response.AssetResponse;
import com.impact.core.module.asset.service.AssetService;
import com.impact.core.module.currency.payload.response.SumOfCurrency;
import com.impact.core.util.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * REST controller for managing assets.
 * Provides endpoints for CRUD operations, inventory statistics,
 * and status updates for assets.
 */
@RestController
@RequestMapping("/api/asset")
@RequiredArgsConstructor
public class AssetController {
    public final AssetService assetService;

    /**
     * Retrieves a specific asset by its ID.
     *
     * @param id the asset's ID
     * @return the asset wrapped in a response
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<AssetResponse>> getAsset(@PathVariable int id) {
        AssetResponse assetResponse = assetService.findByIdR(id);

        return ResponseEntity.ok(ResponseWrapper.<AssetResponse>builder()
                .message("Activo encontrado.")
                .data(assetResponse)
                .build());
    }

    /**
     * Retrieves all registered assets.
     *
     * @return list of all assets
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<List<AssetResponse>>> getAllAssets() {
        List<AssetResponse> assetResponses = assetService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<AssetResponse>>builder()
                .message("Lista de activos.")
                .data(assetResponses)
                .build());
    }

    /**
     * Registers a new asset in the system.
     *
     * @param assetRequest the request payload
     * @return the created asset
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<AssetResponse>> saveAsset(@Valid @RequestBody AssetRequest assetRequest) {
        AssetResponse assetResponse = assetService.save(assetRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.<AssetResponse>builder()
                .message("Activo guardado.")
                .data(assetResponse)
                .build());
    }

    /**
     * Updates an existing asset.
     *
     * @param id           the asset ID to update
     * @param assetRequest the updated asset information
     * @return the updated asset
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<AssetResponse>> updateAsset(@PathVariable int id, @Valid @RequestBody AssetRequest assetRequest) {
        AssetResponse assetResponse = assetService.update(id, assetRequest);

        return ResponseEntity.ok(ResponseWrapper.<AssetResponse>builder()
                .message("Activo actualizado.")
                .data(assetResponse)
                .build());
    }

    /**
     * Soft deletes an asset.
     *
     * @param id the ID of the asset to delete
     * @return the deleted asset
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<AssetResponse>> deleteAsset(@PathVariable int id) {
        AssetResponse assetResponse = assetService.delete(id);

        return ResponseEntity.ok(ResponseWrapper.<AssetResponse>builder()
                .message("Activo eliminado.")
                .data(assetResponse)
                .build());
    }

    /**
     * Calculates the total inventory value per currency for a given date range.
     *
     * @param start_date start date of the range
     * @param end_date   end date of the range
     * @return list of currency totals
     */
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

    /**
     * Updates the status of an asset manually.
     *
     * @param statusId the new status ID
     * @param assetId  the asset's ID
     * @return response with no content
     */
    @PutMapping("/{statusId}/{assetId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<Void>> updateStatus(@PathVariable Integer statusId, @PathVariable String assetId){
        assetService.updateStatus(statusId,assetId);

        return ResponseEntity.ok(ResponseWrapper.<Void>builder()
                .message("Cambio de estado de activo a DISPONIBLE.")
                .build());
    }

    /**
     * Sets an asset's status to "Loaned" (Prestado).
     *
     * @param assetId the asset's ID
     * @return response with no content
     */
    @PutMapping("/assetRequestAccept/{assetId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') ")
    public ResponseEntity<ResponseWrapper<Void>> updateStatusAccepted( @PathVariable String assetId){
        assetService.updateStatus(3,assetId); // 3: ASSET_STATUS_LOANED

        return ResponseEntity.ok(ResponseWrapper.<Void>builder()
                .message("Cambio de estado de activo a PRESTADO.")
                .build());
    }

    /**
     * Retrieves the count of assets purchased between two dates, grouped by date.
     *
     * @param startDate start date of the range
     * @param endDate   end date of the range
     * @return map of dates to asset count
     */
    @GetMapping("/by-purchase-date")
    public ResponseEntity<Map<String, Long>> getAssetsByPurchaseDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(assetService.getAssetsByPurchaseDate(startDate, endDate));
    }
}
