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

@RestController
@RequestMapping("/api/asset-status")
@RequiredArgsConstructor
public class AssetStatusController {
    public final AssetStatusService assetStatusService;

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
