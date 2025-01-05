package com.impact.core.module.auditLog.controller;

import com.impact.core.module.auditLog.payload.response.AuditLogResponse;
import com.impact.core.module.auditLog.service.AuditLogService;
import com.impact.core.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auditLog")
@RequiredArgsConstructor
public class AuditLogController {
    public final AuditLogService auditLogService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<List<AuditLogResponse>>> getAllAuditLogs() {
        List<AuditLogResponse> auditLogResponses = auditLogService.findAll();

        return ResponseEntity.ok(ApiResponse.<List<AuditLogResponse>>builder()
                .message("Lista de logs de auditoría.")
                .data(auditLogResponses)
                .build());
    }
}
