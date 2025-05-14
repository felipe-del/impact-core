package com.impact.core.module.auditLog.controller;

import com.impact.core.module.auditLog.payload.response.AuditLogResponse;
import com.impact.core.module.auditLog.service.AuditLogService;
import com.impact.core.util.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller responsible for handling requests related to audit logs.
 * It provides endpoints for retrieving audit log data.
 */
@RestController
@RequestMapping("/api/auditLog")
@RequiredArgsConstructor
public class AuditLogController {
    public final AuditLogService auditLogService;

    /**
     * Endpoint to get all audit logs.
     * Accessible only by users with the 'ADMINISTRATOR' or 'MANAGER' roles.
     *
     * @return ResponseEntity containing the list of audit log responses and a message
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<AuditLogResponse>>> getAllAuditLogs() {
        List<AuditLogResponse> auditLogResponses = auditLogService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<AuditLogResponse>>builder()
                .message("Lista de logs de auditoría.")
                .data(auditLogResponses)
                .build());
    }
}
