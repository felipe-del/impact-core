package com.impact.core.module.brand.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandRequest {
    @NotBlank(message = "El nombre de la marca no puede estar vacío")
    String name;
}
