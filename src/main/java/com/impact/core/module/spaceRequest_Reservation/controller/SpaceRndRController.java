package com.impact.core.module.spaceRequest_Reservation.controller;

import com.impact.core.module.spaceRequest_Reservation.payload.request.SpaceRndRRequest;
import com.impact.core.module.spaceRequest_Reservation.payload.response.SpaceRndRResponse;
import com.impact.core.module.spaceRequest_Reservation.service.SpaceRndRService;
import com.impact.core.security.service.UserDetailsImpl;
import com.impact.core.util.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/space-request&reservation")
@RequiredArgsConstructor
public class SpaceRndRController {

    public final SpaceRndRService spaceRndRService;

    @PostMapping
//    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
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
}

