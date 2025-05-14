package com.impact.core.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.impact.core.expection.payload.ErrorDataResponse;
import com.impact.core.util.ResponseWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Custom authentication entry point for handling unauthorized access attempts
 * <p>
 *     This class is invoked whenever an authenticated user tries to access a secured resource.
 *     It returns a structured JSON response containing error details.
 * </p>
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    /**
     * Handles authentication exceptions by sending a 401 unauthorized response
     * with a structured error message in JSON format.
     * @param request
     * The {@link HttpServletRequest} that resulted in an authentication exception
     * @param response
     * The {@link HttpServletResponse} to send the error details
     * @param authException
     * The exception that was thrown due to an authentication failure
     * @throws IOException
     * If an input or output exception occurs
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        logger.error("Unauthorized error: {}", authException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ErrorDataResponse errorDataResponse = ErrorDataResponse.builder()
                .status(HttpServletResponse.SC_UNAUTHORIZED)
                .error("Unauthorized")
                .message(authException.getMessage())
                .path(request.getServletPath())
                .build();

        ResponseWrapper<ErrorDataResponse> responseWrapper = ResponseWrapper.<ErrorDataResponse>builder()
                .message("No autorizado")
                .data(errorDataResponse)
                .build();

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), responseWrapper);
    }

}
