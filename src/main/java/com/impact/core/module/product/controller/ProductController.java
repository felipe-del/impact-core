package com.impact.core.module.product.controller;

import com.impact.core.module.product.payload.request.ProductRequestDTO;
import com.impact.core.module.product.payload.response.ProductResponseDTO;
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
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getAllProducts() {
        List<ProductResponseDTO> productResponsDTOS = productService.findAll();

        return ResponseEntity.ok(ApiResponse.<List<ProductResponseDTO>>builder()
                .message("Lista de productos.")
                .data(productResponsDTOS)
                .build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> saveProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        ProductResponseDTO productResponseDTO = productService.save(productRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<ProductResponseDTO>builder()
                .message("Producto guardado correctamente.")
                .data(productResponseDTO)
                .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> updateProduct(@PathVariable int id, @Valid @RequestBody ProductRequestDTO productRequestDTO) {
        ProductResponseDTO productResponseDTO = productService.update(id, productRequestDTO);

        return ResponseEntity.ok(ApiResponse.<ProductResponseDTO>builder()
                .message("Producto actualizado correctamente.")
                .data(productResponseDTO)
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> deleteProduct(@PathVariable int id) {
        ProductResponseDTO productResponseDTO = productService.delete(id);

        return ResponseEntity.ok(ApiResponse.<ProductResponseDTO>builder()
                .message("Producto eliminado correctamente.")
                .data(productResponseDTO)
                .build());
    }


}
