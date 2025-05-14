package com.impact.core.module.product.controller;

import com.impact.core.module.product.entity.Product;
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

/**
 * Controller class for managing {@link Product} entities.
 * <p>
 * This class provides endpoints for performing operations such as saving, updating, deleting,
 * and retrieving products, as well as interacting with product statuses and inventory.
 */
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    public final ProductService productService;

    /**
     * Retrieves a list of all products.
     * <p>
     * This endpoint is available to users with roles "ADMINISTRATOR", "MANAGER", or "TEACHER".
     *
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper} with a list of {@link ProductResponseDTO} objects
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<List<ProductResponseDTO>>> getAllProducts() {
        List<ProductResponseDTO> productResponsDTOS = productService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<ProductResponseDTO>>builder()
                .message("Lista de productos.")
                .data(productResponsDTOS)
                .build());
    }

    /**
     * Saves a new product.
     * <p>
     * This endpoint is available to users with roles "ADMINISTRATOR" or "MANAGER". It saves the product
     * using the provided {@link ProductRequestDTO}.
     *
     * @param productRequestDTO the DTO containing the product details to save
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper} with the saved product details
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<ArrayList<ProductResponseDTO>>> saveProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        ArrayList<ProductResponseDTO> productResponseDTOS = productService.save(productRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.<ArrayList<ProductResponseDTO>>builder()
                .message("Producto guardado correctamente.")
                .data(productResponseDTOS)
                .build());
    }

    /**
     * Updates an existing product.
     * <p>
     * This endpoint is available to users with roles "ADMINISTRATOR" or "MANAGER". It updates the product
     * identified by {@code id} using the details in the provided {@link ProductRequestDTO}.
     *
     * @param id the ID of the product to update
     * @param productRequestDTO the DTO containing the updated product details
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper} with the updated product details
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<ProductResponseDTO>> updateProduct(@PathVariable int id, @Valid @RequestBody ProductRequestDTO productRequestDTO) {
        ProductResponseDTO productResponseDTO = productService.update(id, productRequestDTO);

        return ResponseEntity.ok(ResponseWrapper.<ProductResponseDTO>builder()
                .message("Producto actualizado correctamente.")
                .data(productResponseDTO)
                .build());
    }

    /**
     * Deletes a product.
     * <p>
     * This endpoint is available to users with roles "ADMINISTRATOR" or "MANAGER". It deletes the product
     * identified by {@code id}.
     *
     * @param id the ID of the product to delete
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper} with the deleted product details
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<ProductResponseDTO>> deleteProduct(@PathVariable int id) {
        ProductResponseDTO productResponseDTO = productService.delete(id);

        return ResponseEntity.ok(ResponseWrapper.<ProductResponseDTO>builder()
                .message("Producto eliminado correctamente.")
                .data(productResponseDTO)
                .build());
    }

    /**
     * Updates the status of a product.
     * <p>
     * This endpoint is available to users with roles "ADMINISTRATOR", "MANAGER", or "TEACHER". It updates the
     * status of the product identified by {@code productId} to the status identified by {@code statusId}.
     *
     * @param statusId the ID of the new status
     * @param productId the ID of the product whose status will be updated
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper} indicating the success of the operation
     */
    @PutMapping("/{statusId}/{productId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('TEACHER')")
    public ResponseEntity<ResponseWrapper<Void>> updateStatus(@PathVariable Integer statusId, @PathVariable Integer productId){
        productService.updateStatus(statusId,productId);

        return ResponseEntity.ok(ResponseWrapper.<Void>builder()
                .message("Cambio de estado de producto a DISPONIBLE.")
                .build());
    }

    /**
     * Updates the status of a product to "LOANED".
     * <p>
     * This endpoint is available to users with roles "ADMINISTRATOR" or "MANAGER". It updates the status of the
     * product identified by {@code productId} to "LOANED" (status ID 3).
     *
     * @param productId the ID of the product whose status will be updated to "LOANED"
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper} indicating the success of the operation
     */
    @PutMapping("/productRequestAccept/{productId}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') ")
    public ResponseEntity<ResponseWrapper<Void>> updateStatusAccepted(@PathVariable Integer productId){
        productService.updateStatus(3,productId); //status 3: ASSET_STATUS_LOANED

        return ResponseEntity.ok(ResponseWrapper.<Void>builder()
                .message("Cambio de estado de producto a PRESTADO.")
                .build());
    }

    /**
     * Retrieves the number of available products in inventory.
     * <p>
     * This endpoint is available to users with roles "ADMINISTRATOR" or "MANAGER". It returns the number of
     * products that are available in the inventory.
     *
     * @return a {@link ResponseEntity} containing a {@link ResponseWrapper} with the count of available products
     */
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
