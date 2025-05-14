package com.impact.core.module.assetStatus.controller;

import com.impact.core.module.assetStatus.payload.response.AssetStatusResponse;
import com.impact.core.module.assetStatus.service.AssetStatusService;
import com.impact.core.util.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for handling requests related to asset statuses.
 * Provides endpoints for retrieving all available asset statuses.
 */
@RestController
@RequestMapping("/api/asset-status")
@RequiredArgsConstructor
public class AssetStatusController {
    public final AssetStatusService assetStatusService;

    /**
     * Endpoint to retrieve a list of all asset statuses.
     * Accessible only to users with the 'ADMINISTRATOR' or 'MANAGER' roles.
     *
     * @return A {@link ResponseEntity} containing a wrapped list of {@link AssetStatusResponse}
     *         objects with a success message.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<AssetStatusResponse>>> getAllAssetStatus() {
        List<AssetStatusResponse> assetStatusResponses = assetStatusService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<AssetStatusResponse>>builder()
                .message("Lista de estados de activo.")
                .data(assetStatusResponses)
                .build());
    }

}
