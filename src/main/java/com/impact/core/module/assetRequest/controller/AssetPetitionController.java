package com.impact.core.module.assetRequest.controller;


import com.impact.core.module.assetRequest.payload.request.AssetPetitionDTORequest;
import com.impact.core.module.assetRequest.payload.response.AssetPetitionDTOResponse;
import com.impact.core.module.assetRequest.service.AssetPetitionService;
import com.impact.core.security.service.UserDetailsImpl;
import com.impact.core.util.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/asset-petition")
@RequiredArgsConstructor
public class AssetPetitionController {

    public final AssetPetitionService assetPetitionService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<List<AssetPetitionDTOResponse>>> getAllAssetPetitions() {
        List<AssetPetitionDTOResponse> assetPetitionDTOResponses = assetPetitionService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<AssetPetitionDTOResponse>>builder()
                .message("Lista de solicitudes de activos.")
                .data(assetPetitionDTOResponses)
                .build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<AssetPetitionDTOResponse>> saveAssetPetition(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody AssetPetitionDTORequest assetPetitionDTORequest) {
        AssetPetitionDTOResponse assetPetitionDTOResponse = assetPetitionService.save(userDetails, assetPetitionDTORequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.<AssetPetitionDTOResponse>builder()
                .message("Solicitud de activo guardada correctamente.")
                .data(assetPetitionDTOResponse)
                .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<AssetPetitionDTOResponse>> updateAssetPetition(
            @PathVariable int id, @Valid @RequestBody AssetPetitionDTORequest assetPetitionDTORequest) {
        AssetPetitionDTOResponse assetPetitionDTOResponse = assetPetitionService.update(id, assetPetitionDTORequest);

        return ResponseEntity.ok(ResponseWrapper.<AssetPetitionDTOResponse>builder()
                .message("Solicitud de activo actualizada correctamente.")
                .data(assetPetitionDTOResponse)
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<Void>> deleteAssetPetition(@PathVariable int id) {
        assetPetitionService.delete(id);

        return ResponseEntity.ok(ResponseWrapper.<Void>builder()
                .message("Solicitud de activo eliminada correctamente.")
                .build());
    }

}
