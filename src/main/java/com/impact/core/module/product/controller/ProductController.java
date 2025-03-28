package com.impact.core.module.product.controller;

import com.impact.core.module.product.payload.request.ProductRequestDTO;
import com.impact.core.module.product.payload.response.ProductResponseDTO;
import com.impact.core.module.product.service.ProductService;
import com.impact.core.util.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    public final ProductService productService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<List<ProductResponseDTO>>> getAllProducts() {
        List<ProductResponseDTO> productResponsDTOS = productService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<ProductResponseDTO>>builder()
                .message("Lista de productos.")
                .data(productResponsDTOS)
                .build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<ArrayList<ProductResponseDTO>>> saveProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        ArrayList<ProductResponseDTO> productResponseDTOS = productService.save(productRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.<ArrayList<ProductResponseDTO>>builder()
                .message("Producto guardado correctamente.")
                .data(productResponseDTOS)
                .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<ProductResponseDTO>> updateProduct(@PathVariable int id, @Valid @RequestBody ProductRequestDTO productRequestDTO) {
        ProductResponseDTO productResponseDTO = productService.update(id, productRequestDTO);

        return ResponseEntity.ok(ResponseWrapper.<ProductResponseDTO>builder()
                .message("Producto actualizado correctamente.")
                .data(productResponseDTO)
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<ProductResponseDTO>> deleteProduct(@PathVariable int id) {
        ProductResponseDTO productResponseDTO = productService.delete(id);

        return ResponseEntity.ok(ResponseWrapper.<ProductResponseDTO>builder()
                .message("Producto eliminado correctamente.")
                .data(productResponseDTO)
                .build());
    }

    @PutMapping("/{statusId}/{productId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<Void>> updateStatus(@PathVariable Integer statusId, @PathVariable Integer productId){
        productService.updateStatus(statusId,productId);

        return ResponseEntity.ok(ResponseWrapper.<Void>builder()
                .message("Cambio de estado de producto a DISPONIBLE.")
                .build());
    }

    @PutMapping("/productRequestAccept/{productId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') ")
    public ResponseEntity<ResponseWrapper<Void>> updateStatusAccepted(@PathVariable Integer productId){
        productService.updateStatus(3,productId); //status 3: ASSET_STATUS_LOANED

        return ResponseEntity.ok(ResponseWrapper.<Void>builder()
                .message("Cambio de estado de producto a PRESTADO.")
                .build());
    }

    @GetMapping("/remaining-products")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') ")
    public ResponseEntity<ResponseWrapper<Long>>  remainingProductsInInventory() {
        Long remainingProducts = productService.remainingProductsInInventory();

        return ResponseEntity.ok(ResponseWrapper.<Long>builder()
                .message("Cantidad de productos disponibles en el inventario")
                .data(remainingProducts)
                .build());
    }
}
