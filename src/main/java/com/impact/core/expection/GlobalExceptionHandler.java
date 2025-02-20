package com.impact.core.expection;

import com.impact.core.expection.customException.ConflictException;
import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.expection.customException.UnauthorizedException;
import com.impact.core.expection.payload.ErrorDataResponse;
import com.impact.core.util.ResponseWrapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseWrapper<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();

        Map<String, String> errors = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        ResponseWrapper<Map<String, String>> response = ResponseWrapper.<Map<String, String>>builder()
                .message("Los datos enviados no son válidos")
                .data(errors)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // BAD REQUEST

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ResponseWrapper<ErrorDataResponse>> handleConflict(ConflictException ex, WebRequest request) {
        return handleException(HttpStatus.CONFLICT, ex.getMessage(), "Conflicto", request);
    }

    // INTERNAL SERVER ERROR

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseWrapper<ErrorDataResponse>> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        return handleException(HttpStatus.NOT_FOUND, ex.getMessage(), "Recurso no encontrado", request);
    }

    // SERVICE UNAVAILABLE

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ResponseWrapper<ErrorDataResponse>> handleUnauthorized(UnauthorizedException ex, WebRequest request) {
        return handleException(HttpStatus.UNAUTHORIZED, ex.getMessage(), "No autorizado", request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseWrapper<ErrorDataResponse>> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        return handleException(HttpStatus.BAD_REQUEST, ex.getMessage(), "Argumento no válido", request);
    }

    // JPQL EXCEPTION

    @ExceptionHandler({DataIntegrityViolationException.class, SQLIntegrityConstraintViolationException.class})
    public ResponseEntity<ResponseWrapper<ErrorDataResponse>> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        Throwable rootCause = ex.getRootCause();
        String detailedMessage = (rootCause != null) ? rootCause.getMessage() : ex.getMessage();

        String userMessage = "Asegúrese de que los datos enviados no violen restricciones de integridad.";
        return handleException(HttpStatus.CONFLICT, detailedMessage, userMessage, request);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private ResponseEntity<ResponseWrapper<ErrorDataResponse>> handleException(HttpStatus httpStatus, String message, String userMessage, WebRequest request) {
        String path = getPath(request);

        // Build the error data
        ErrorDataResponse errorDataResponse = ErrorDataResponse.builder()
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .message(message)
                .path(path)
                .build();

        // Build the response
        ResponseWrapper<ErrorDataResponse> response = ResponseWrapper.<ErrorDataResponse>builder()
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
