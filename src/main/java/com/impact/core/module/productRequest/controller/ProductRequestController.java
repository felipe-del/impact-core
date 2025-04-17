package com.impact.core.module.productRequest.controller;

import com.impact.core.module.productRequest.payload.request.ProductRequestDTORequest;
import com.impact.core.module.productRequest.payload.response.ProductRequestDTOResponse;
import com.impact.core.module.productRequest.service.ProductRequestService;
import com.impact.core.module.resource_request_status.enun.EResourceRequestStatus;
import com.impact.core.module.resource_request_status.payload.request.CancelRequestDTO;
import com.impact.core.security.service.UserDetailsImpl;
import com.impact.core.util.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-request")
@RequiredArgsConstructor
public class ProductRequestController {
    public final ProductRequestService productRequestService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<List<ProductRequestDTOResponse>>> getAllProductRequests() {
        List<ProductRequestDTOResponse> productRequestDTOResponses = productRequestService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<ProductRequestDTOResponse>>builder()
                .message("Lista de solicitudes de productos.")
                .data(productRequestDTOResponses)
                .build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<ProductRequestDTOResponse>> saveProductRequest(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody ProductRequestDTORequest productRequestDTORequest) {
        ProductRequestDTOResponse ProductRequestDTOResponse = productRequestService.save(userDetails, productRequestDTORequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.<ProductRequestDTOResponse>builder()
                .message("Solicitud de producto guardada correctamente.")
                .data(ProductRequestDTOResponse)
                .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<ProductRequestDTOResponse>> updateProductRequest(
            @PathVariable int id, @Valid @RequestBody ProductRequestDTORequest productRequestDTORequest) {
        ProductRequestDTOResponse ProductRequestDTOResponse = productRequestService.update(id, productRequestDTORequest);

        return ResponseEntity.ok(ResponseWrapper.<ProductRequestDTOResponse>builder()
                .message("Solicitud de producto actualizada correctamente.")
                .data(ProductRequestDTOResponse)
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<ProductRequestDTOResponse>> deleteProductRequest(@PathVariable int id) {
        ProductRequestDTOResponse ProductRequestDTOResponse = productRequestService.delete(id);

        return ResponseEntity.ok(ResponseWrapper.<ProductRequestDTOResponse>builder()
                .message("Solicitud de producto eliminada correctamente.")
                .data(ProductRequestDTOResponse)
                .build());
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<List<ProductRequestDTOResponse>>> getMyRequests(@PathVariable Integer id){
        List<ProductRequestDTOResponse> productRequestDTOResponse = productRequestService.findByUser(id);

        return ResponseEntity.ok(ResponseWrapper.<List<ProductRequestDTOResponse>>builder()
                .message("Lista de solicitudes de productos por usuario.")
                .data(productRequestDTOResponse)
                .build());
    }
    @PutMapping("/cancel/{productRId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<Void>> cancelRequest(@PathVariable Integer productRId,
                                                               @Valid @RequestBody CancelRequestDTO cancelReason){
        productRequestService.cancelRequest(4,productRId, 1, cancelReason.getCancelReason()); //status 4: RESOURCE_REQUEST_STATUS_CANCELED (resource_request_status)
                                                                                //status 1: PRODUCT_STATUS_AVAILABLE (product_status)
        return ResponseEntity.ok(ResponseWrapper.<Void>builder()
                .message("Cambio de estado de solicitud a cancelado.")
                .build());
    }
    @PutMapping("/accept/{productRId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') ")
    public ResponseEntity<ResponseWrapper<Void>> acceptRequest(@PathVariable Integer productRId){
        productRequestService.acceptRequest(2,productRId); //status 4: RESOURCE_REQUEST_STATUS_ACCEPTED (resource_request_status)

        return ResponseEntity.ok(ResponseWrapper.<Void>builder()
                .message("Cambio de estado de solicitud a aceptado.")
                .build());
    }
    /**
     * Get all product requests excluding EARRING and RENEWAL statuses.
     * @return List of product requests excluding EARRING and RENEWAL statuses.
     */
    @GetMapping("/filter/excluding-earring-renewal")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<List<ProductRequestDTOResponse>>> getProductRequestsExcludingEarringAndRenewal() {
        List<ProductRequestDTOResponse> filteredProductRequests = productRequestService.findAllExcludingEarringAndRenewal();

        return ResponseEntity.ok(ResponseWrapper.<List<ProductRequestDTOResponse>>builder()
                .message("Lista de solicitudes de productos excluyendo estados EARRING y RENEWAL.")
                .data(filteredProductRequests)
                .build());
    }

}
