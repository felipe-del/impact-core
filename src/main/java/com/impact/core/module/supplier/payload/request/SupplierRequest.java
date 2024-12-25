package com.impact.core.module.supplier.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierRequest {
    @NotBlank(message = "El nombre es requerido")
    private String name;
    @NotBlank(message = "El teléfono es requerido")
    private String phone;
    @NotBlank(message = "El email es requerido")
    @Email(message = "El email no es válido")
    private String email;
    @NotBlank(message = "La dirección es requerida")
    private String address;
    @NotBlank(message = "El tipo de entidad es requerido")
    private String entityTypeName;
    @NotBlank(message = "El contacto del cliente es requerido")
    private String clientContact;
    @NotBlank(message = "El número de identificación es requerido")
    private String idNumber;

}
