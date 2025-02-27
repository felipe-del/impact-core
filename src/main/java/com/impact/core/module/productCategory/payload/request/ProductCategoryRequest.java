package com.impact.core.module.productCategory.payload.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryRequest {
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;
    @Min(value = 1, message = "La cantidad mínima no puede ser menor a 1")
    private int minimumQuantity;
    @Min(value = 1, message = "El id de la categoría no puede ser menor a 1")
    private int categoryTypeId;
    @Min(value = 1, message = "El id de la unidad de medida no puede ser menor a 1")
    private int unitOfMeasurementId;
}
