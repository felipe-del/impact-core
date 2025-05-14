package com.impact.core.module.supplier.entity;

import com.impact.core.module.supplier.enun.EEntityType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity Type class representing a type of entity used in the Supplier module.
 * <p>
 * This class defines the different types of entities (e.g., physical, legal) a supplier can be associated with.
 */
@Getter
@Setter
@Entity
@Table(name = "entity_type")
public class EntityType {

    /**
     * Unique identifier for the Entity Type.
     */
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * Name of the Entity Type (e.g., PHYSICAL or LEGAL).
     * The value is represented as an enumeration of the Entity Type ({@link EEntityType}).
     */
    @Size(max = 50)
    @NotNull
    @Column(name = "type_name", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private EEntityType typeName;

}