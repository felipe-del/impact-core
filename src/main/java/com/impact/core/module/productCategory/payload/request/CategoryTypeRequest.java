package com.impact.core.module.productCategory.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryTypeRequest {
    @NotBlank(message = "El nombre es requerido")
    private String name;
    @NotBlank(message = "La descripción es requerida")
    private String description;
}
