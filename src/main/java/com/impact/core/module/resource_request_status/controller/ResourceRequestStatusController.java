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

@RestController
@RequestMapping("api/resource-request-status")
@RequiredArgsConstructor
public class ResourceRequestStatusController {
    public final ResourceRequestStatusService resourceRequestStatusService;

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
