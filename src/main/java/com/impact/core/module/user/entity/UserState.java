package com.impact.core.module.user.entity;

import com.impact.core.module.user.enun.EUserState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the state of a user in the system. This class is mapped to the 'user_state' table in the database.
 * It defines the possible states a user can have, such as active, inactive, or suspended.
 */
@Getter
@Setter
@Entity
@Table(name = "user_state")
public class UserState {

    /**
     * The unique identifier for the user state in the database.
     */
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * The name of the user state. This is an enumerated field of type {@link EUserState}.
     * The field is required and can be one of the defined states (e.g., ACTIVE, INACTIVE, SUSPENDED).
     */
    @Size(max = 50)
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, length = 50)
    private EUserState name;

    /**
     * A description of the user state. This field is optional and can store detailed information about the state.
     */
    @Lob
    @Column(name = "description")
    private String description;

}