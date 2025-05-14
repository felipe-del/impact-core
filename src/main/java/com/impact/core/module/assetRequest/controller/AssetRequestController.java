package com.impact.core.module.assetRequest.controller;

import com.impact.core.module.assetRequest.payload.renew.AssetRequestDTORenew;
import com.impact.core.module.assetRequest.payload.request.AssetRequestDTORequest;
import com.impact.core.module.assetRequest.payload.response.AssetRequestDTOResponse;
import com.impact.core.module.assetRequest.service.AssetRequestService;
import com.impact.core.module.productRequest.payload.response.ProductRequestDTOResponse;
import com.impact.core.module.resource_request_status.payload.request.CancelRequestDTO;
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

/**
 * Controller for managing asset requests.
 * Provides endpoints for retrieving, creating, updating, and deleting asset requests.
 * Only authorized users with specific roles can access these endpoints.
 */
@RestController
@RequestMapping("/api/asset-request")
@RequiredArgsConstructor
public class AssetRequestController {

    public final AssetRequestService assetRequestService;

    /**
     * Retrieves all asset requests.
     * Only accessible by users with roles: ADMINISTRATOR, MANAGER, or TEACHER.
     *
     * @return ResponseWrapper containing a list of {@link AssetRequestDTOResponse} objects.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<List<AssetRequestDTOResponse>>> getAllAssetRequests() {
        List<AssetRequestDTOResponse> assetRequestDTOResponses = assetRequestService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<AssetRequestDTOResponse>>builder()
                .message("Lista de solicitudes de activos.")
                .data(assetRequestDTOResponses)
                .build());
    }

    /**
     * Retrieves all asset requests related to renewals.
     * Only accessible by users with roles: ADMINISTRATOR or MANAGER.
     *
     * @return ResponseWrapper containing a list of {@link AssetRequestDTOResponse} objects.
     */
    @GetMapping("/renewal-request")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<AssetRequestDTOResponse>>> getAllAssetRequestsRenewal() {
        List<AssetRequestDTOResponse> assetRequestDTOResponses = assetRequestService.findAllRenewal();

        return ResponseEntity.ok(ResponseWrapper.<List<AssetRequestDTOResponse>>builder()
                .message("Lista de solicitudes de renovación de activos.")
                .data(assetRequestDTOResponses)
                .build());
    }


    /**
     * Retrieves a specific asset request by ID.
     * Only accessible by users with roles: ADMINISTRATOR, MANAGER, or TEACHER.
     *
     * @param id The ID of the asset request to retrieve.
     * @return ResponseWrapper containing the {@link AssetRequestDTOResponse} for the specified request.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<AssetRequestDTOResponse>> getAssetRequest(@PathVariable int id) {
        AssetRequestDTOResponse assetRequestResponse = assetRequestService.findByIdDTO(id);

        return ResponseEntity.ok(ResponseWrapper.<AssetRequestDTOResponse>builder()
                .message("Solicitud de activo encontrada.")
                .data(assetRequestResponse)
                .build());
    }


    /**
     * Creates a new asset request for renewal.
     * Only accessible by users with roles: ADMINISTRATOR, MANAGER, or TEACHER.
     *
     * @param userDetails The details of the authenticated user making the request.
     * @param assetRequestDTORenew The {@link AssetRequestDTORenew} to create.
     * @return ResponseWrapper containing the created {@link AssetRequestDTOResponse}.
     */
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


    /**
     * Creates a new asset request.
     * Only accessible by users with roles: ADMINISTRATOR, MANAGER, or TEACHER.
     *
     * @param userDetails The details of the authenticated user making the request.
     * @param assetRequestDTORequest The {@link AssetRequestDTORequest} to create.
     * @return ResponseWrapper containing the created {@link AssetRequestDTOResponse}.
     */
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

    /**
     * Updates an existing asset request by ID.
     * Only accessible by users with roles: ADMINISTRATOR, MANAGER, or TEACHER.
     *
     * @param id The ID of the asset request to update.
     * @param assetRequestDTORequest The updated {@link AssetRequestDTORequest}.
     * @return ResponseWrapper containing the updated {@link AssetRequestDTOResponse}.
     */
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

    /**
     * Updates an existing asset request renewal by ID.
     * Only accessible by users with roles: ADMINISTRATOR, MANAGER, or TEACHER.
     *
     * @param id The ID of the asset request renewal to update.
     * @param assetRequestDTORequest The updated {@link AssetRequestDTORequest}.
     * @return ResponseWrapper containing the updated {@link AssetRequestDTOResponse}.
     */
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

    /**
     * Deletes an asset request by ID.
     * Only accessible by users with roles: ADMINISTRATOR, MANAGER, or TEACHER.
     *
     * @param id The ID of the asset request to delete.
     * @return ResponseWrapper indicating the successful deletion.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<Void>> deleteAssetRequest(@PathVariable int id) {
        assetRequestService.delete(id);

        return ResponseEntity.ok(ResponseWrapper.<Void>builder()
                .message("Solicitud de activo eliminada correctamente.")
                .build());
    }

    /**
     * Retrieves all asset requests for a specific user by their ID.
     * Only accessible by users with roles: ADMINISTRATOR, MANAGER, or TEACHER.
     *
     * @param id The ID of the user whose asset requests are to be retrieved.
     * @return ResponseWrapper containing a list of {@link AssetRequestDTOResponse} objects for the user.
     */
    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<List<AssetRequestDTOResponse>>> getMyRequests(@PathVariable int id){
        List<AssetRequestDTOResponse> assetRequestDTOResponses = assetRequestService.findByUser(id);

        return ResponseEntity.ok(ResponseWrapper.<List<AssetRequestDTOResponse>>builder()
                .message("Lista de solicitudes de activos por usuario.")
                .data(assetRequestDTOResponses)
                .build());
    }

    /**
     * Updates the status of an asset request to {@code canceled}.
     * Only accessible by users with roles: ADMINISTRATOR, MANAGER, or TEACHER.
     *
     * @param statusId The status ID to update.
     * @param assetRequestId The ID of the asset request.
     * @param cancelRequestDTO The reason for canceling the asset request.
     * @return ResponseWrapper indicating the status update.
     */
    @PutMapping("/{statusId}/{assetRequestId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<Void>> updateStatus(@PathVariable Integer statusId, @PathVariable Integer assetRequestId,
                                                              @Valid @RequestBody CancelRequestDTO cancelRequestDTO){
        assetRequestService.updateStatus(statusId, assetRequestId, cancelRequestDTO.getCancelReason());

        return ResponseEntity.ok(ResponseWrapper.<Void>builder()
                .message("Cambio de estado de solicitud a cancelado.")
                .build());
    }

    /**
     * Accepts the renewal of an asset request.
     * Only accessible by users with roles: ADMINISTRATOR or MANAGER.
     *
     * @param assetRequestId The ID of the asset request renewal.
     * @return ResponseWrapper containing the updated {@link AssetRequestDTOResponse}.
     */
    @PutMapping("/accept-renewal/{assetRequestId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<AssetRequestDTOResponse>> renewUpdateStatusAccepted(@PathVariable Integer assetRequestId){
        assetRequestService.updateRenewalStatusAccepted(assetRequestId);
        AssetRequestDTOResponse assetRequestResponseAfterUpdate = assetRequestService.findByIdDTO(assetRequestId);

        return ResponseEntity.ok(ResponseWrapper.<AssetRequestDTOResponse>builder()
                .data(assetRequestResponseAfterUpdate)
                .message("La renovación de solicitud de activo fue aceptada.")
                .build());
    }

    /**
     * Rejects the renewal of an asset request.
     * Only accessible by users with roles: ADMINISTRATOR or MANAGER.
     *
     * @param assetRequestId The ID of the asset request renewal.
     * @return ResponseWrapper containing the updated {@link AssetRequestDTOResponse}.
     */
    @PutMapping("/reject-renewal/{assetRequestId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<AssetRequestDTOResponse>> renewUpdateStatusRejected(@PathVariable Integer assetRequestId){
        assetRequestService.updateRenewalStatusRejected(assetRequestId);
        AssetRequestDTOResponse assetRequestResponseAfterUpdate = assetRequestService.findByIdDTO(assetRequestId);

        return ResponseEntity.ok(ResponseWrapper.<AssetRequestDTOResponse>builder()
                .data(assetRequestResponseAfterUpdate)
                .message("La renovación de solicitud de activo fue denegada.")
                .build());
    }

    /**
     * Retrieves all asset requests excluding EARRING and RENEWAL statuses.
     * Only accessible by users with roles: ADMINISTRATOR, MANAGER, or TEACHER.
     *
     * @return ResponseWrapper containing a list of filtered {@link AssetRequestDTOResponse} objects.
     */
    @GetMapping("/filter/excluding-earring-renewal")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<List<AssetRequestDTOResponse>>> getAssetRequestsExcludingEarringAndRenewal() {
        List<AssetRequestDTOResponse> filteredAssetRequests = assetRequestService.findAllExcludingEarringAndRenewal();

        return ResponseEntity.ok(ResponseWrapper.<List<AssetRequestDTOResponse>>builder()
                .message("Lista de solicitudes de activos excluyendo estados EARRING y RENEWAL.")
                .data(filteredAssetRequests)
                .build());
    }

    /**
     * Retrieves all asset requests with EARRING status.
     * Only accessible by users with roles: ADMINISTRATOR, MANAGER, or TEACHER.
     *
     * @return ResponseWrapper containing a list of filtered {@link AssetRequestDTOResponse} objects with EARRING status.
     */
    @GetMapping("/filter/earring")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<List<AssetRequestDTOResponse>>> getAssetRequestsWithEarring() {
        List<AssetRequestDTOResponse> filteredAssetRequests = assetRequestService.findAllWithEarring();

        return ResponseEntity.ok(ResponseWrapper.<List<AssetRequestDTOResponse>>builder()
                .message("Lista de solicitudes de activos con estado EARRING.")
                .data(filteredAssetRequests)
                .build());
    }

    /**
     * Accepts an asset request.
     * Only accessible by users with roles: ADMINISTRATOR or MANAGER.
     *
     * @param assetRequestId The ID of the asset request to accept.
     * @return ResponseWrapper containing the accepted {@link AssetRequestDTOResponse}.
     */
    @PutMapping("/accept/{assetRequestId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') ")
    public ResponseEntity<ResponseWrapper<AssetRequestDTOResponse>> acceptAssetRequest(@PathVariable Integer assetRequestId){
        AssetRequestDTOResponse response = assetRequestService.acceptRequest(assetRequestId);

        return ResponseEntity.ok(ResponseWrapper.<AssetRequestDTOResponse>builder()
                .message("Solicitud de activo aceptada.")
                .data(response)
                .build());
    }

    /**
     * Rejects an asset request.
     * Only accessible by users with roles: ADMINISTRATOR, MANAGER, or TEACHER.
     *
     * @param assetRequestId The ID of the asset request to reject.
     * @return ResponseWrapper containing the rejected {@link AssetRequestDTOResponse}.
     */
    @PostMapping("/reject/{assetRequestId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<AssetRequestDTOResponse>> rejectAssetRequest(@PathVariable Integer assetRequestId){
        AssetRequestDTOResponse response = assetRequestService.rejectRequest(assetRequestId);

        return ResponseEntity.ok(ResponseWrapper.<AssetRequestDTOResponse>builder()
                .message("Solicitud de activo rechazada.")
                .data(response)
                .build());
    }
}
