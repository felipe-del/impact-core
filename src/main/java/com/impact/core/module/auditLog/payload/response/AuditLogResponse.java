package com.impact.core.module.auditLog.payload.response;

import com.impact.core.module.user.payload.response.UserResponse;
import lombok.*;

import java.time.Instant;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogResponse {
    private int id;
    private String entityName;
    private String action;
    private String details;
    private Instant timestamp;
    private UserResponse user;
}
