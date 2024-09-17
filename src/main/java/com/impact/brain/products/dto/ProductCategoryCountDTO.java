package com.impact.brain.products.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductCategoryCountDTO {
    private Integer id;
        private String name;
        private String unitOfMeasurement;
        private Integer cantidadMinima;
        private Long availableQuantity;  // Contador de productos disponibles
        private String status ;  // "Requerido" o "Suficiente"
        private String productCategory;

        @Override
        public String toString() {
            return "ProductCategoryCountDTO{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", unitOfMeasurement='" + unitOfMeasurement + '\'' +
                    ", cantidadMinima=" + cantidadMinima +
                    ", availableQuantity=" + availableQuantity +
                    ", status='" + status + '\'' +
                    '}';
        }
}
