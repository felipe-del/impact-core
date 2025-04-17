package com.impact.core.module.spaceRequest_Reservation.controller;
import com.impact.core.module.assetRequest.payload.response.AssetRequestDTOResponse;
import com.impact.core.module.resource_request_status.payload.request.CancelRequestDTO;
import com.impact.core.module.spaceRequest_Reservation.payload.request.SpaceRndRRequest;
import com.impact.core.module.spaceRequest_Reservation.payload.response.SpaceRndRResponse;
import com.impact.core.module.spaceRequest_Reservation.service.SpaceRndRService;
import com.impact.core.security.service.UserDetailsImpl;
import com.impact.core.util.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/space-request&reservation")
@RequiredArgsConstructor
public class SpaceRndRController {

    public final SpaceRndRService spaceRndRService;

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<SpaceRndRResponse>> createSpaceRndR(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody SpaceRndRRequest spaceRndRRequest) {

        SpaceRndRResponse spaceRndRResponse = spaceRndRService.save(userDetails, spaceRndRRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ResponseWrapper.<SpaceRndRResponse>builder()
                        .message("Solicitud y reservación de espacio creadas correctamente")
                        .data(spaceRndRResponse)
                        .build()
        );
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<List<SpaceRndRResponse>>> getAllSpaceRndR() {
        List<SpaceRndRResponse> responses =  spaceRndRService.getAll();

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseWrapper.<List<SpaceRndRResponse>>builder()
                        .message("Lista de todas las solicitudes y reservaciones")
                        .data(responses)
                        .build()
        );
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<List<SpaceRndRResponse>>> getMyRequests(@PathVariable Integer id){
        List<SpaceRndRResponse> spaceRndRRequest = spaceRndRService.getByUser(id);

        return ResponseEntity.ok(ResponseWrapper.<List<SpaceRndRResponse>>builder()
                .message("Lista de solicitudes de espacios por usuario.")
                .data(spaceRndRRequest)
                .build());
    }

    @PutMapping("/{reqId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<Void>> cancelRequest(@PathVariable Integer reqId, @Valid @RequestBody CancelRequestDTO cancelRequestDTO){
        spaceRndRService.cancelRequest(4, reqId, cancelRequestDTO.getCancelReason());//status 4: RESOURCE_REQUEST_STATUS_CANCELED (resource_request_status)

        return ResponseEntity.ok(ResponseWrapper.<Void>builder()
                .message("Cambio de estado de solicitud a cancelado.")
                .build());
    }

    /**
     * Get all space requests and reservations excluding EARRING and RENEWAL statuses.
     * @return List of space requests and reservations excluding EARRING and RENEWAL statuses.
     */
    @GetMapping("/filter/excluding-earring-renewal")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<List<SpaceRndRResponse>>> getSpaceRequestsExcludingEarringAndRenewal() {
        List<SpaceRndRResponse> filteredRequests = spaceRndRService.findAllExcludingEarringAndRenewal();

        return ResponseEntity.ok(ResponseWrapper.<List<SpaceRndRResponse>>builder()
                .message("Lista de solicitudes de espacios excluyendo estados EARRING y RENEWAL.")
                .data(filteredRequests)
                .build());
    }

    /**
     * Get all space requests and reservations with EARRING status.
     * @return List of space requests and reservations with EARRING status.
     */
    @GetMapping("/filter/earring")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<List<SpaceRndRResponse>>> getSpaceRequestsWithEarring() {
        List<SpaceRndRResponse> filteredRequests = spaceRndRService.findAllWithEarring();

        return ResponseEntity.ok(ResponseWrapper.<List<SpaceRndRResponse>>builder()
                .message("Lista de solicitudes de espacios con estado EARRING.")
                .data(filteredRequests)
                .build());
    }

    @PutMapping("/accept/{spaceRequestId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') ")
    public ResponseEntity<ResponseWrapper<SpaceRndRResponse>> acceptSpaceRequest(@PathVariable Integer spaceRequestId){
        SpaceRndRResponse response = spaceRndRService.acceptRequest(spaceRequestId);

        return ResponseEntity.ok(ResponseWrapper.<SpaceRndRResponse>builder()
                .message("Solicitud de espacio aceptada.")
                .data(response)
                .build());
    }

}

