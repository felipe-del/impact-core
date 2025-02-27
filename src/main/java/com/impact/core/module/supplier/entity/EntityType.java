package com.impact.core.module.supplier.entity;

import com.impact.core.module.supplier.enun.EEntityType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "entity_type")
public class EntityType {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "type_name", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private EEntityType typeName;

}