package com.impact.core.expection;

import com.impact.core.expection.customException.ConflictException;
import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.expection.customException.UnauthorizedException;
import com.impact.core.expection.payload.ErrorDataResponse;
import com.impact.core.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();

        Map<String, String> errors = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        ApiResponse<Map<String, String>> response = ApiResponse.<Map<String, String>>builder()
                .message("Los datos enviados no son válidos")
                .data(errors)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // BAD REQUEST

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiResponse<ErrorDataResponse>> handleConflict(ConflictException ex, WebRequest request) {
        return handleException(HttpStatus.CONFLICT, ex.getMessage(), "Conflicto", request);
    }

    // INTERNAL SERVER ERROR

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<ErrorDataResponse>> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        return handleException(HttpStatus.NOT_FOUND, ex.getMessage(), "Recurso no encontrado", request);
    }

    // SERVICE UNAVAILABLE

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<ErrorDataResponse>> handleUnauthorized(UnauthorizedException ex, WebRequest request) {
        return handleException(HttpStatus.UNAUTHORIZED, ex.getMessage(), "No autorizado", request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<ErrorDataResponse>> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        return handleException(HttpStatus.BAD_REQUEST, ex.getMessage(), "Argumento no válido", request);
    }


    /// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private ResponseEntity<ApiResponse<ErrorDataResponse>> handleException(HttpStatus httpStatus, String message, String userMessage, WebRequest request) {
        String path = getPath(request);

        // Build the error data
        ErrorDataResponse errorDataResponse = ErrorDataResponse.builder()
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .message(message)
                .path(path)
                .build();

        // Build the response
        ApiResponse<ErrorDataResponse> response = ApiResponse.<ErrorDataResponse>builder()
                .message(userMessage)
                .data(errorDataResponse)
                .build();

        // Return the response
        return new ResponseEntity<>(response, httpStatus);
    }

    private String getPath(WebRequest request) {
        return request.getDescription(false);
    }
}
