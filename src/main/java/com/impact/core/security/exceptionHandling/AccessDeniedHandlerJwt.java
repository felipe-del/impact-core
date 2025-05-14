package com.impact.core.security.exceptionHandling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.impact.core.expection.payload.ErrorDataResponse;
import com.impact.core.util.ResponseWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Custom handler for access denied exceptions within the Spring Security context.
 * <p>
 *     This handler is triggered when an authenticated user attempts to a resource
 *     for which they do not possess sufficient permissions. It responds with a structured
 *     JSON payload and 403 forbidden status.
 * </p>
 */
@Component
public class AccessDeniedHandlerJwt implements AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(AccessDeniedHandlerJwt.class);

    /**
     * Handles access denied exceptions by returning a JSON-formatted error response with a
     * HTTP 403 status
     * @param request
     * The {@link HttpServletRequest} that triggered the exception
     * @param response
     * The {@link HttpServletResponse} to write the error details to
     * @param accessDeniedException
     * The exception representing the access denial
     * @throws IOException
     * If an input or output exception occurs
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException {

        logger.error("Forbidden error: {}", accessDeniedException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        ErrorDataResponse errorDataResponse = ErrorDataResponse.builder()
                .status(HttpServletResponse.SC_FORBIDDEN)
                .error("Forbidden")
                .message(accessDeniedException.getMessage())
                .path(request.getServletPath())
                .build();

        ResponseWrapper<ErrorDataResponse> responseWrapper = ResponseWrapper.<ErrorDataResponse>builder()
                .message("Acceso prohibido")
                .data(errorDataResponse)
                .build();

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), responseWrapper);
    }
}