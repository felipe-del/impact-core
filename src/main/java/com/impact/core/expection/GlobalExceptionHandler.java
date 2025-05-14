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

/**
 * Global exception handler for mapping specific exceptions to standardized API responses.
 * <p>
 *     This class uses {@link ControllerAdvice} to intercept and process exceptions
 *     thrown during the execution of controller methods, returning meaningful HTTP responses.
 * </p>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation errors triggered by {@code valid} annotations
     * @param ex
     * The validation exception containing binding result details
     * @return
     * A response with all the field-specific validation errors
     */
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

    /**
     * Handles custom conflict exceptions (e.g., duplicate records).
     */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ResponseWrapper<ErrorDataResponse>> handleConflict(ConflictException ex, WebRequest request) {
        return handleException(HttpStatus.CONFLICT, ex.getMessage(), "Conflicto", request);
    }

    /**
     * Handles resource not found exceptions.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseWrapper<ErrorDataResponse>> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        return handleException(HttpStatus.NOT_FOUND, ex.getMessage(), "Recurso no encontrado", request);
    }

    /**
     * Handles unauthorized access attempts.
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ResponseWrapper<ErrorDataResponse>> handleUnauthorized(UnauthorizedException ex, WebRequest request) {
        return handleException(HttpStatus.UNAUTHORIZED, ex.getMessage(), "No autorizado", request);
    }

    /**
     * Handles illegal arguments passed to controller methods.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseWrapper<ErrorDataResponse>> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        return handleException(HttpStatus.BAD_REQUEST, ex.getMessage(), "Argumento no válido", request);
    }

    /**
     * Handles data integrity violations (e.g., foreign key or unique constraint failures).
     */
    @ExceptionHandler({DataIntegrityViolationException.class, SQLIntegrityConstraintViolationException.class})
    public ResponseEntity<ResponseWrapper<ErrorDataResponse>> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        Throwable rootCause = ex.getRootCause();
        String detailedMessage = (rootCause != null) ? rootCause.getMessage() : ex.getMessage();

        String userMessage = "Asegúrese de que los datos enviados no violen restricciones de integridad.";
        return handleException(HttpStatus.CONFLICT, detailedMessage, userMessage, request);
    }

    /**
     * Generic method to construct a consistent error response.
     *
     * @param httpStatus  The HTTP status to be returned.
     * @param message     The detailed technical error message.
     * @param userMessage A user-friendly description of the error.
     * @param request     The original web request.
     * @return A standardized {@code ResponseWrapper} containing {@code ErrorDataResponse}.
     */
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

    /**
     * Extracts the request path from the {@link WebRequest}.
     */
    private String getPath(WebRequest request) {
        return request.getDescription(false);
    }
}
