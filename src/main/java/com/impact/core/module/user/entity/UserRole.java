package com.impact.core.module.user.entity;

import com.impact.core.module.user.enun.EUserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a user role in the system. This class is mapped to the 'user_role' table in the database.
 * It defines the different roles a user can have in the system, such as Administrator, Manager, or Teacher.
 */
@Getter
@Setter
@Entity
@Table(name = "user_role")
public class UserRole {

    /**
     * The unique identifier for the user role in the database.
     */
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * The name of the user role. This is an enumerated field of type {@link EUserRole}.
     * The field is a required field and can be one of the defined roles (e.g., ADMINISTRATOR, MANAGER, TEACHER).
     */
    @Size(max = 50)
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, length = 50)
    private EUserRole name;

    /**
     * A description of the user role. This field is optional and can store a detailed description of the role's responsibilities.
     */
    @Lob
    @Column(name = "description")
    private String description;

}