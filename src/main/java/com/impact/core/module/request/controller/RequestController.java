package com.impact.core.module.request.controller;

import com.impact.core.module.request.payload.RequestDTOResponse;
import com.impact.core.module.request.service.RequestService;
import com.impact.core.util.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller that handles HTTP requests related to product, asset, and space requests.
 * <p>
 * This controller defines the endpoints for retrieving the list of pending requests
 * across different request types (Asset, Product, and Space). The requests are returned
 * in a unified response format.
 */
@RestController
@RequestMapping("/api/request")
@RequiredArgsConstructor
public class RequestController {
    public final RequestService requestService;

    /**
     * Retrieves a list of all pending requests.
     * <p>
     * This endpoint allows the client to fetch all pending requests from different
     * request types (Asset, Product, and Space) in a unified format.
     *
     * @return A {@link ResponseEntity} containing a {@link ResponseWrapper} with a list
     *         of {@link RequestDTOResponse} objects representing the pending requests.
     */
    @GetMapping
    public ResponseEntity<ResponseWrapper<List<RequestDTOResponse>>> getAllPendingRequest() {
        List<RequestDTOResponse> requestDTOResponses = requestService.getAllPendingRequests();

        return ResponseEntity.ok(ResponseWrapper.<List<RequestDTOResponse>>builder()
                .message("Lista de solicitudes de productos.")
                .data(requestDTOResponses)
                .build());
    }
}
