package com.impact.core.module.spaceRequest_Reservation.controller;
import com.impact.core.module.resource_request_status.payload.request.CancelRequestDTO;
import com.impact.core.module.spaceRequest_Reservation.payload.request.SpaceRequestAndReservationRequest;
import com.impact.core.module.spaceRequest_Reservation.payload.response.SpaceRequestAndReservationResponse;
import com.impact.core.module.spaceRequest_Reservation.service.SpaceRequestAndReservationService;
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
 * Controller class for managing space requests and reservations.
 * <p>
 * This controller exposes the necessary endpoints for creating, retrieving, updating, and deleting space requests and reservations.
 * The endpoints include features like accepting or rejecting requests and filtering requests based on different statuses.
 */
@RestController
@RequestMapping("/api/space-request&reservation")
@RequiredArgsConstructor
public class SpaceRequestAndReservationController {

    public final SpaceRequestAndReservationService spaceRndRService;

    /**
     * Creates a new space request and reservation.
     * <p>
     * This endpoint allows users with the roles 'ADMINISTRATOR', 'MANAGER', or 'TEACHER' to create a new space request and reservation.
     * The request body must contain valid space reservation details.
     *
     * @param userDetails The authenticated user's details.
     * @param spaceRequestAndReservationRequest The space request and reservation details.
     * @return A {@link ResponseEntity} containing a {@link ResponseWrapper} with the created space request and reservation data.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<SpaceRequestAndReservationResponse>> createSpaceRndR(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody SpaceRequestAndReservationRequest spaceRequestAndReservationRequest) {

        SpaceRequestAndReservationResponse spaceRequestAndReservationResponse = spaceRndRService.save(userDetails, spaceRequestAndReservationRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseWrapper.<SpaceRequestAndReservationResponse>builder()
                        .message("Solicitud y reservación de espacio creadas correctamente")
                        .data(spaceRequestAndReservationResponse)
                        .build()
        );
    }

    /**
     * Retrieves all space requests and reservations.
     * <p>
     * This endpoint retrieves a list of all space requests and reservations for users with the roles 'ADMINISTRATOR', 'MANAGER', or 'TEACHER'.
     *
     * @return A {@link ResponseEntity} containing a {@link ResponseWrapper} with the list of all space requests and reservations.
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<List<SpaceRequestAndReservationResponse>>> getAllSpaceRndR() {
        List<SpaceRequestAndReservationResponse> responses =  spaceRndRService.getAll();

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseWrapper.<List<SpaceRequestAndReservationResponse>>builder()
                        .message("Lista de todas las solicitudes y reservaciones")
                        .data(responses)
                        .build()
        );
    }

    /**
     * Retrieves space requests for a specific user.
     * <p>
     * This endpoint retrieves a list of space requests made by a specific user, identified by the user's ID.
     *
     * @param id The ID of the user whose space requests are to be retrieved.
     * @return A {@link ResponseEntity} containing a {@link ResponseWrapper} with the list of the user's space requests.
     */
    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<List<SpaceRequestAndReservationResponse>>> getMyRequests(@PathVariable Integer id){
        List<SpaceRequestAndReservationResponse> spaceRndRRequest = spaceRndRService.getByUser(id);

        return ResponseEntity.ok(ResponseWrapper.<List<SpaceRequestAndReservationResponse>>builder()
                .message("Lista de solicitudes de espacios por usuario.")
                .data(spaceRndRRequest)
                .build());
    }

    /**
     * Cancels a space request by updating its status to {@code CANCELED}.
     * <p>
     * This endpoint allows a user with the role 'ADMINISTRATOR', 'MANAGER', or 'TEACHER' to cancel a space request.
     * The space request's status is updated to canceled (status 4).
     *
     * @param reqId The ID of the space request to cancel.
     * @param cancelRequestDTO The details of the cancellation request, including the reason.
     * @return A {@link ResponseEntity} containing a {@link ResponseWrapper} with a message indicating the status update.
     */
    @PutMapping("/{reqId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<Void>> cancelRequest(@PathVariable Integer reqId, @Valid @RequestBody CancelRequestDTO cancelRequestDTO){
        spaceRndRService.cancelRequest(4, reqId, cancelRequestDTO.getCancelReason()); //status 4: RESOURCE_REQUEST_STATUS_CANCELED

        return ResponseEntity.ok(ResponseWrapper.<Void>builder()
                .message("Cambio de estado de solicitud a cancelado.")
                .build());
    }

    /**
     * Retrieves all space requests and reservations excluding EARRING and RENEWAL statuses.
     * <p>
     * This endpoint allows users with the roles 'ADMINISTRATOR', 'MANAGER', or 'TEACHER' to retrieve a list of space requests and reservations,
     * excluding those with the EARRING and RENEWAL statuses.
     *
     * @return A {@link ResponseEntity} containing a {@link ResponseWrapper} with the filtered list of space requests and reservations.
     */
    @GetMapping("/filter/excluding-earring-renewal")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<List<SpaceRequestAndReservationResponse>>> getSpaceRequestsExcludingEarringAndRenewal() {
        List<SpaceRequestAndReservationResponse> filteredRequests = spaceRndRService.findAllExcludingEarringAndRenewal();

        return ResponseEntity.ok(ResponseWrapper.<List<SpaceRequestAndReservationResponse>>builder()
                .message("Lista de solicitudes de espacios excluyendo estados EARRING y RENEWAL.")
                .data(filteredRequests)
                .build());
    }

    /**
     * Retrieves all space requests and reservations with the EARRING status.
     * <p>
     * This endpoint allows users with the roles 'ADMINISTRATOR', 'MANAGER', or 'TEACHER' to retrieve a list of space requests and reservations,
     * including only those with the EARRING status.
     *
     * @return A {@link ResponseEntity} containing a {@link ResponseWrapper} with the filtered list of space requests and reservations.
     */
    @GetMapping("/filter/earring")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<List<SpaceRequestAndReservationResponse>>> getSpaceRequestsWithEarring() {
        List<SpaceRequestAndReservationResponse> filteredRequests = spaceRndRService.findAllWithEarring();

        return ResponseEntity.ok(ResponseWrapper.<List<SpaceRequestAndReservationResponse>>builder()
                .message("Lista de solicitudes de espacios con estado EARRING.")
                .data(filteredRequests)
                .build());
    }

    /**
     * Accepts a space request, updating its status to accepted.
     * <p>
     * This endpoint allows users with the roles 'ADMINISTRATOR' or 'MANAGER' to accept a space request.
     *
     * @param spaceRequestId The ID of the space request to accept.
     * @return A {@link ResponseEntity} containing a {@link ResponseWrapper} with the accepted space request data.
     */
    @PutMapping("/accept/{spaceRequestId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') ")
    public ResponseEntity<ResponseWrapper<SpaceRequestAndReservationResponse>> acceptSpaceRequest(@PathVariable Integer spaceRequestId){
        SpaceRequestAndReservationResponse response = spaceRndRService.acceptRequest(spaceRequestId);

        return ResponseEntity.ok(ResponseWrapper.<SpaceRequestAndReservationResponse>builder()
                .message("Solicitud de espacio aceptada.")
                .data(response)
                .build());
    }

    /**
     * Rejects a space request, updating its status to rejected.
     * <p>
     * This endpoint allows users with the roles 'ADMINISTRATOR' or 'MANAGER' to reject a space request.
     *
     * @param spaceRequestId The ID of the space request to reject.
     * @return A {@link ResponseEntity} containing a {@link ResponseWrapper} with the rejected space request data.
     */
    @PostMapping("/reject/{spaceRequestId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') ")
    public ResponseEntity<ResponseWrapper<SpaceRequestAndReservationResponse>> rejectSpaceRequest(@PathVariable Integer spaceRequestId){
        SpaceRequestAndReservationResponse response = spaceRndRService.rejectRequest(spaceRequestId);

        return ResponseEntity.ok(ResponseWrapper.<SpaceRequestAndReservationResponse>builder()
                .message("Solicitud de espacio rechazada.")
                .data(response)
                .build());
    }

}

