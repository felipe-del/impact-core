package com.impact.core.module.auditLog.entity;

import com.impact.core.module.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * Entity class representing an audit log in the system.
 * Stores information about actions performed on entities within the system, including
 * the entity name, action performed, additional details, timestamp, and the user who performed the action.
 */
@Getter
@Setter
@Entity
@Builder
@Table(name = "audit_log")
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    /**
     * Unique identifier for the audit log entry.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * The name of the entity on which the action was performed
     */
    @Size(max = 50)
    @NotNull
    @Column(name = "entity_name", nullable = false, length = 50)
    private String entityName;

    /**
     * The type of action performed
     */
    @Size(max = 50)
    @NotNull
    @Column(name = "action", nullable = false, length = 50)
    private String action;

    /**
     * Additional details regarding the action performed.
     */
    @Lob
    @Column(name = "details")
    private String details;

    /**
     * Timestamp indicating when the action was performed.
     * This is automatically set to the current timestamp before persisting the log.
     */
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "timestamp")
    private Instant timestamp;

    /**
     * Lifecycle callback to set the timestamp to the current time before persisting the audit log.
     */
    @PrePersist
    public void prePersist() {
        this.timestamp = Instant.now();
    }

    /**
     * The user who performed the action.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}