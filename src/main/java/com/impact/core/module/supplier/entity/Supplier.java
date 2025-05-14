package com.impact.core.module.supplier.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Supplier class representing a supplier entity in the Supplier module.
 * <p>
 * This class contains the details of a supplier including its contact information, associated entity type, and identification details.
 */
@Getter
@Setter
@Entity
@Builder
@Table(name = "supplier")
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {

    /**
     * Unique identifier for the Supplier.
     * This field is automatically generated using the identity strategy.
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Name of the Supplier.
     * This field is mandatory and cannot be null.
     */
    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * Phone number of the Supplier.
     * This field has a maximum length of 100 characters.
     */
    @Size(max = 100)
    @Column(name = "phone", length = 100)
    private String phone;

    /**
     * Email address of the Supplier.
     * This field has a maximum length of 100 characters.
     */
    @Size(max = 100)
    @Column(name = "email", length = 100)
    private String email;

    /**
     * Address of the Supplier.
     * This field is stored as a large object (LOB) for potentially large text content.
     */
    @Lob
    @Column(name = "address")
    private String address;

    /**
     * The type of entity associated with the Supplier (e.g., physical or legal).
     * This field references the {@link EntityType} class.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_type_id")
    private EntityType entityType;

    /**
     * Client contact name associated with the Supplier.
     * This field has a maximum length of 100 characters.
     */
    @Size(max = 100)
    @Column(name = "client_contact", length = 100)
    private String clientContact;

    /**
     * Identification number of the Supplier.
     * This field has a maximum length of 50 characters.
     */
    @Size(max = 50)
    @Column(name = "id_number", length = 50)
    private String idNumber;

}