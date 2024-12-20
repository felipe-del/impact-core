package com.impact.core.security.exceptionHandling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.impact.core.expection.payload.ErrorDataResponse;
import com.impact.core.util.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AccessDeniedHandlerJwt implements AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(AccessDeniedHandlerJwt.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        logger.error("Forbidden error: {}", accessDeniedException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        ErrorDataResponse errorDataResponse = ErrorDataResponse.builder()
                .status(HttpServletResponse.SC_FORBIDDEN)
                .error("Forbidden")
                .message("No tienes permisos para acceder a este recurso.")
                .path(request.getServletPath())
                .build();

        ApiResponse<ErrorDataResponse> apiResponse = ApiResponse.<ErrorDataResponse>builder()
                .message("Acceso prohibido")
                .data(errorDataResponse)
                .build();

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), apiResponse);
    }
}