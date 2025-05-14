package com.impact.core.module.spaceStatus.entity;

import com.impact.core.module.spaceStatus.enun.ESpaceStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity representing a space status in the system.
 * <p>
 * This entity is mapped to the `space_status` table in the database and stores information about
 * the different statuses a space can have, such as availability, loan status, and maintenance.
 */
@Getter
@Setter
@Entity
@Table(name = "space_status")
public class SpaceStatus {

    /**
     * Unique identifier for each space status.
     * <p>
     * This is the primary key for the `space_status` table.
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The name of the space status.
     * <p>
     * This field corresponds to the `name` column in the database and uses an enum to represent
     * different statuses (such as available, loaned, etc.).
     * It cannot be null.
     */
    @Size(max = 50)
    @NotNull
    @Column(name = "name", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private ESpaceStatus name;

    /**
     * A description of the space status.
     * <p>
     * This field corresponds to the `description` column in the database and provides additional
     * details about the space status.
     */
    @Lob
    @Column(name = "description")
    private String description;
}