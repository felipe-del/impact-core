package com.impact.core.expection.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorData {
    private int status; // Código de estado HTTP (401, 403, etc.)
    private String error; // Nombre del error (Forbidden, Unauthorized, etc.)
    private String message; // Descripción del error
    private String path; // Ruta donde ocurrió el error
}
