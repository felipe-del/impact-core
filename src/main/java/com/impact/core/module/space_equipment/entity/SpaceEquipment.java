package com.impact.core.module.space_equipment.entity;

import com.impact.core.module.brand.entity.Brand;
import com.impact.core.module.space.entity.Space;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Represents the equipment assigned to a specific space within the system.
 * <p>
 * Each {@code SpaceEquipment} instance links a specific brand of equipment
 * to a space and specifies the quantity available.
 */
@Getter
@Setter
@Entity
@Builder
@Table(name = "space_equipment")
@NoArgsConstructor
@AllArgsConstructor
public class SpaceEquipment {

    /**
     * Unique identifier for the {@code SpaceEquipment} entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * Name of the equipment.
     * This field is mandatory and its length must not exceed 100 characters.
     */
    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;


    /**
     * Brand associated with the equipment.
     * This is a mandatory association to the {@link Brand} entity.
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    /**
     * Space to which the equipment is assigned.
     * This is a mandatory association to the {@link Space} entity.
     */
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "space_id", nullable = false)
    private Space space;


    /**
     * Quantity of this equipment available in the specified space.
     */
    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

}