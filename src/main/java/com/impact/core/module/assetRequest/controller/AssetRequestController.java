package com.impact.core.module.assetRequest.controller;


import com.impact.core.module.assetRequest.entity.AssetRequest;
import com.impact.core.module.assetRequest.payload.renew.AssetRequestDTORenew;
import com.impact.core.module.assetRequest.payload.request.AssetRequestDTORequest;
import com.impact.core.module.assetRequest.payload.response.AssetRequestDTOResponse;
import com.impact.core.module.assetRequest.service.AssetRequestService;
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
@RequestMapping("/api/asset-request")
@RequiredArgsConstructor
public class AssetRequestController {

    public final AssetRequestService assetRequestService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<List<AssetRequestDTOResponse>>> getAllAssetRequests() {
        List<AssetRequestDTOResponse> assetRequestDTOResponses = assetRequestService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<AssetRequestDTOResponse>>builder()
                .message("Lista de solicitudes de activos.")
                .data(assetRequestDTOResponses)
                .build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<AssetRequestDTOResponse>> getAssetRequest(@PathVariable int id) {
        AssetRequestDTOResponse assetRequestResponse = assetRequestService.findByIdDTO(id);

        return ResponseEntity.ok(ResponseWrapper.<AssetRequestDTOResponse>builder()
                .message("Solicitud de activo encontrada.")
                .data(assetRequestResponse)
                .build());
    }

    @PostMapping("/renew")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<AssetRequestDTOResponse>> saveAssetRequestRenew(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody AssetRequestDTORenew assetRequestDTORenew) {
        AssetRequestDTOResponse assetRequestDTOResponse = assetRequestService.saveRenew(userDetails, assetRequestDTORenew);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.<AssetRequestDTOResponse>builder()
                .message("Solicitud de activo guardada correctamente.")
                .data(assetRequestDTOResponse)
                .build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<AssetRequestDTOResponse>> saveAssetRequest(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody AssetRequestDTORequest assetRequestDTORequest) {
        AssetRequestDTOResponse assetRequestDTOResponse = assetRequestService.save(userDetails, assetRequestDTORequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.<AssetRequestDTOResponse>builder()
                .message("Solicitud de activo guardada correctamente.")
                .data(assetRequestDTOResponse)
                .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<AssetRequestDTOResponse>> updateAssetRequest(
            @PathVariable int id, @Valid @RequestBody AssetRequestDTORequest assetRequestDTORequest) {
        AssetRequestDTOResponse assetRequestDTOResponse = assetRequestService.update(id, assetRequestDTORequest);

        return ResponseEntity.ok(ResponseWrapper.<AssetRequestDTOResponse>builder()
                .message("Solicitud de activo actualizada correctamente.")
                .data(assetRequestDTOResponse)
                .build());
    }

    @PutMapping("/renew/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<AssetRequestDTOResponse>> updateAssetRequestRenew(
            @PathVariable int id, @Valid @RequestBody AssetRequestDTORequest assetRequestDTORequest) {
        AssetRequestDTOResponse assetRequestDTOResponse = assetRequestService.updateRenew(id, assetRequestDTORequest);

        return ResponseEntity.ok(ResponseWrapper.<AssetRequestDTOResponse>builder()
                .message("Solicitud de activo actualizada correctamente.")
                .data(assetRequestDTOResponse)
                .build());
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<Void>> deleteAssetRequest(@PathVariable int id) {
        assetRequestService.delete(id);

        return ResponseEntity.ok(ResponseWrapper.<Void>builder()
                .message("Solicitud de activo eliminada correctamente.")
                .build());
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<List<AssetRequestDTOResponse>>> getMyRequests(@PathVariable int id){
        List<AssetRequestDTOResponse> assetRequestDTOResponses = assetRequestService.findByUser(id);

        return ResponseEntity.ok(ResponseWrapper.<List<AssetRequestDTOResponse>>builder()
                .message("Lista de solicitudes de activos por usuario.")
                .data(assetRequestDTOResponses)
                .build());
    }

    @PutMapping("/{statusId}/{assetRequestId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<Void>> updateStatus(@PathVariable Integer statusId, @PathVariable Integer assetRequestId){
        assetRequestService.updateStatus(statusId,assetRequestId);

        return ResponseEntity.ok(ResponseWrapper.<Void>builder()
                .message("Cambio de estado de solicitud a cancelado.")
                .build());
    }

}
