package com.impact.core.module.space_equipment.payload.response;

import com.impact.core.module.brand.payload.response.BrandResponse;
import com.impact.core.module.space.payload.response.SpaceResponse;
import lombok.*;

/**
 * Data Transfer Object (DTO) for representing a response containing space equipment information.
 * <p>
 * This class encapsulates the details of a space equipment, including its name, associated brand, space, and quantity.
 * It is used for transferring data between layers in the application (e.g., from service to controller).
 * </p>
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpaceEquipmentResponse {

    /**
     * The unique identifier of the space equipment.
     */
    private int id;

    /**
     * The name of the space equipment.
     */
    private String name;

    /**
     * The brand associated with the space equipment.
     */
    private BrandResponse brandResponse;

    /**
     * The space where the equipment is located.
     */
    private SpaceResponse spaceResponse;

    /**
     * The quantity of the space equipment available.
     */
    private int quantity;
}
