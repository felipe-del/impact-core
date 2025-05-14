package com.impact.core.module.resource_request_status.controller;

import com.impact.core.module.resource_request_status.payload.response.ResourceRequestStatusResponse;
import com.impact.core.module.resource_request_status.service.ResourceRequestStatusService;
import com.impact.core.util.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class for managing resource request statuses.
 * <p>
 * This controller exposes endpoints for fetching the list of resource request statuses.
 * Access is restricted based on user roles (only users with 'ADMINISTRATOR' or 'MANAGER' roles can access the endpoint).
 */
@RestController
@RequestMapping("api/resource-request-status")
@RequiredArgsConstructor
public class ResourceRequestStatusController {
    public final ResourceRequestStatusService resourceRequestStatusService;

    /**
     * Retrieves a list of all resource request statuses.
     * <p>
     * The method uses the {@link ResourceRequestStatusService} to fetch the statuses and wraps the result in a response
     * structure. The response includes a message and a list of status data.
     * Access to this endpoint is restricted to users with 'ADMINISTRATOR' or 'MANAGER' roles.
     *
     * @return a {@link ResponseEntity} containing a response wrapper with the list of resource request statuses
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<ResourceRequestStatusResponse>>> getAllResourceRequestStatus() {
        List<ResourceRequestStatusResponse> resourceRequestStatusResponses = resourceRequestStatusService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<ResourceRequestStatusResponse>>builder()
                .message("Lista de estados de solicitud de recursos.")
                .data(resourceRequestStatusResponses)
                .build());
    }
}
