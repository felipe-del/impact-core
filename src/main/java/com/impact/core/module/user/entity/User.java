package com.impact.core.module.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.impact.core.module.auditLog.listener.AuditLogListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Represents a user entity in the system. This class is mapped to the 'user' table in the database.
 * It includes information about the user's identity, role, and state.
 * <p>
 * The class uses Jakarta Persistence (JPA) annotations for mapping to the database, and it integrates
 * with an audit logging system through the {@link AuditLogListener}.
 * </p>
 */
@Getter
@Setter
@Builder
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditLogListener.class)
public class User {

    /**
     * The unique identifier for the user in the database.
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The name of the user. It is a required field with a maximum length of 100 characters.
     */
    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * The email address of the user. It is a required field with a maximum length of 100 characters.
     */
    @Size(max = 100)
    @NotNull
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    /**
     * The password of the user. It is a required field with a maximum length of 100 characters.
     * This field is excluded from JSON serialization for security reasons.
     */
    @Size(max = 100)
    @NotNull
    @Column(name = "password", nullable = false, length = 100)
    @JsonIgnore
    private String password;

    /**
     * The role of the user in the system. This is a foreign key relation to the {@link UserRole} entity.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private UserRole role;

    /**
     * The state of the user in the system. This is a foreign key relation to the {@link UserState} entity.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private UserState state;

}