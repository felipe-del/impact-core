package com.impact.core.module.spaceStatus.payload.response;

import com.impact.core.module.spaceStatus.entity.SpaceStatus;
import lombok.*;

/**
 * Data Transfer Object (DTO) representing a response for a {@link SpaceStatus}.
 * <p>
 * This class serves as a response representation for a {@link SpaceStatus} entity, providing essential details such as its
 * identifier, name, and description. It is used to transfer data to the client-side or external systems.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpaceStatusResponse {

    /**
     * The unique identifier of the {@link SpaceStatus}.
     * <p>
     * This field holds the database-generated ID of the {@link SpaceStatus} entity. It uniquely identifies the record in the database.
     */
    private Integer id;

    /**
     * The name of the {@link SpaceStatus}.
     * <p>
     * This field contains the name of the space status, which is typically represented by an enum value.
     */
    private String name;

    /**
     * A description of the {@link SpaceStatus}.
     * <p>
     * This field provides a textual description of the space status, offering additional context about its meaning or usage.
     */
    private String description;
}
