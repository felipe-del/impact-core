package com.impact.core.module.assetStatistics.controller;

import com.impact.core.module.assetStatistics.entity.AssetRequestStatisticsByDate;
import com.impact.core.module.assetStatistics.service.AssetRequestStatiscsByDateService;
import com.impact.core.util.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller that handles HTTP requests related to asset request statistics by date.
 * Provides endpoints for retrieving asset request statistics data.
 */
@RestController
@RequestMapping("/api/asset-loan-statistics")
@RequiredArgsConstructor
public class AssetRequestStatisticsByDateController {
    private final AssetRequestStatiscsByDateService assetRequestStatiscsByDateService;

    /**
     * Endpoint to retrieve all asset request statistics by date.
     * This endpoint fetches statistics and wraps them into a response structure with a message.
     *
     * @return A {@link ResponseEntity} containing a {@link ResponseWrapper} with the list of
     *         {@link AssetRequestStatisticsByDate} and a success message.
     */@GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<AssetRequestStatisticsByDate>>> getAllAssetRequestStatisticsByDates() {
        List<AssetRequestStatisticsByDate> requestStatisticsByDates = assetRequestStatiscsByDateService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<AssetRequestStatisticsByDate>>builder()
                .message("Lista de estadísticas de solicitudes de activos")
                .data(requestStatisticsByDates)
                .build());
    }

}
