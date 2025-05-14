package com.impact.core.module.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

/**
 * Represents a token assigned to a user in the system. This token can be used for authentication or other purposes.
 * The {@link UserToken} entity is mapped to the 'user_token' table in the database.
 */
@Getter
@Setter
@Builder
@Entity
@Table(name = "user_token")
@NoArgsConstructor
@AllArgsConstructor
public class UserToken {

    /**
     * The unique identifier for the user token in the database.
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The {@link User} associated with the token. This is a mandatory relationship and references the 'user' table.
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * The token string. This is a required field that stores the actual token value.
     * The field has a maximum length of 255 characters.
     */
    @Size(max = 255)
    @NotNull
    @Column(name = "token", nullable = false)
    private String token;

    /**
     * The expiration date and time of the token. This is a required field and indicates when the token becomes invalid.
     */
    @NotNull
    @Column(name = "expiry_date", nullable = false)
    private Instant expiryDate;

    /**
     * The timestamp when the token was created. It defaults to the current timestamp when the token is created.
     */
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

}