package com.impact.core.module.auditLog.payload.response;

import com.impact.core.module.user.payload.response.UserResponse;
import lombok.*;

import java.time.Instant;

/**
 * Data Transfer Object (DTO) representing an audit log entry in the response format.
 * This class is used to send audit log information to the client.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogResponse {

    /**
     * The unique identifier of the audit log.
     */
    private int id;

    /**
     * The name of the entity on which the action was performed.
     */
    private String entityName;

    /**
     * The type of action performed (e.g., "INSERT", "UPDATE", "DELETE").
     */
    private String action;

    /**
     * Additional details about the action performed.
     */
    private String details;

    /**
     * The timestamp when the audit log was created.
     */
    private Instant timestamp;

    /**
     * The user who performed the action, represented as a {@link UserResponse}.
     */
    private UserResponse user;
}
