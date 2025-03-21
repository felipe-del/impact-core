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

@RestController
@RequestMapping("/api/request")
@RequiredArgsConstructor
public class RequestController {
    public final RequestService requestService;

    @GetMapping
//    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<RequestDTOResponse>>> getAllPendingRequest() {
        List<RequestDTOResponse> requestDTOResponses = requestService.getAllPendingRequests();

        return ResponseEntity.ok(ResponseWrapper.<List<RequestDTOResponse>>builder()
                .message("Lista de solicitudes de productos.")
                .data(requestDTOResponses)
                .build());
    }
}
