package com.impact.core.module.spaceStatus.controller;

import com.impact.core.module.spaceStatus.payload.response.SpaceStatusResponse;
import com.impact.core.module.spaceStatus.service.SpaceStatusService;
import com.impact.core.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/space-status")
@RequiredArgsConstructor
public class SpaceStatusController {
    public final SpaceStatusService spaceStatusService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<List<SpaceStatusResponse>>> getAllSpaceStatus() {
        List<SpaceStatusResponse> spaceStatusResponses = spaceStatusService.findAll();

        return ResponseEntity.ok(ApiResponse.<List<SpaceStatusResponse>>builder()
                .message("Lista de estados de espacio.")
                .data(spaceStatusResponses)
                .build());
    }

}
