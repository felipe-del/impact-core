package com.impact.core.module.spaceStatus.controller;

import com.impact.core.module.spaceStatus.payload.response.SpaceStatusResponse;
import com.impact.core.module.spaceStatus.service.SpaceStatusService;
import com.impact.core.util.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for handling requests related to space statuses.
 * <p>
 * Provides endpoints to manage and retrieve information about space statuses,
 * including available, loaned, and in maintenance statuses.
 * Access is restricted to users with the roles: `ADMINISTRATOR`, `MANAGER`, or `TEACHER`.
 */
@RestController
@RequestMapping("api/space-status")
@RequiredArgsConstructor
public class SpaceStatusController {
    public final SpaceStatusService spaceStatusService;


    /**
     * Endpoint to retrieve all space statuses.
     * <p>
     * This endpoint returns a list of all space statuses in the system.
     * Only users with the `ADMINISTRATOR`, `MANAGER`, or `TEACHER` role can access this endpoint.
     *
     * @return A list of all space statuses wrapped in a response object.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<List<SpaceStatusResponse>>> getAllSpaceStatus() {
        List<SpaceStatusResponse> spaceStatusResponses = spaceStatusService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<SpaceStatusResponse>>builder()
                .message("Lista de estados de espacio.")
                .data(spaceStatusResponses)
                .build());
    }

}
