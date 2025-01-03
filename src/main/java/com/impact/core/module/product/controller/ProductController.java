package com.impact.core.module.product.controller;

import com.impact.core.module.product.payload.request.ProductRequest;
import com.impact.core.module.product.payload.response.ProductResponse;
import com.impact.core.module.product.service.ProductService;
import com.impact.core.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    public final ProductService productService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts() {
        List<ProductResponse> productResponses = productService.findAll();

        return ResponseEntity.ok(ApiResponse.<List<ProductResponse>>builder()
                .message("Lista de productos.")
                .data(productResponses)
                .build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<ProductResponse>> saveProduct(@Valid @RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.save(productRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<ProductResponse>builder()
                .message("Producto guardado correctamente.")
                .data(productResponse)
                .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(@PathVariable int id, @Valid @RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.update(id, productRequest);

        return ResponseEntity.ok(ApiResponse.<ProductResponse>builder()
                .message("Producto actualizado correctamente.")
                .data(productResponse)
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<ProductResponse>> deleteProduct(@PathVariable int id) {
        ProductResponse productResponse = productService.delete(id);

        return ResponseEntity.ok(ApiResponse.<ProductResponse>builder()
                .message("Producto eliminado correctamente.")
                .data(productResponse)
                .build());
    }


}
