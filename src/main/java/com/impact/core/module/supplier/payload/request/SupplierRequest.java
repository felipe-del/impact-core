package com.impact.core.module.supplier.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Supplier Data Transfer Object (DTO) for handling incoming request data related to supplier creation or updates.
 * <p>
 * This class encapsulates the input values required to register or modify a supplier entity.
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierRequest {

    /**
     * Name of the supplier.
     * <p>
     * This field is required and must not be blank.
     */
    @NotBlank(message = "El nombre es requerido")
    private String name;

    /**
     * Contact phone number of the supplier.
     * <p>
     * This field is required and must not be blank.
     */
    @NotBlank(message = "El teléfono es requerido")
    private String phone;

    /**
     * Email address of the supplier.
     * <p>
     * This field must contain a valid email format and must not be blank.
     */
    @NotBlank(message = "El email es requerido")
    @Email(message = "El email no es válido")
    private String email;

    /**
     * Physical address of the supplier.
     * <p>
     * This field is required and must not be blank.
     */
    @NotBlank(message = "La dirección es requerida")
    private String address;

    /**
     * Type of the entity, e.g., "LEGAL" or "PHYSICAL".
     * <p>
     * This field is required and is used to map the supplier to its corresponding entity type.
     */
    @NotBlank(message = "El tipo de entidad es requerido")
    private String entityTypeName;

    /**
     * Name or identifier of the client contact related to this supplier.
     * <p>
     * This field is required and must not be blank.
     */
    @NotBlank(message = "El contacto del cliente es requerido")
    private String clientContact;

    /**
     * Identification number associated with the supplier, such as a tax ID or national ID.
     * <p>
     * This field is required and must not be blank.
     */
    @NotBlank(message = "El número de identificación es requerido")
    private String idNumber;
}
