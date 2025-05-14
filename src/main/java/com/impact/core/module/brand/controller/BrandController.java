package com.impact.core.module.brand.controller;

import com.impact.core.module.brand.payload.request.BrandRequest;
import com.impact.core.module.brand.payload.response.BrandResponse;
import com.impact.core.module.brand.service.BrandService;
import com.impact.core.util.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for handling brand-related operations such as creating, updating,
 * deleting, and retrieving brands. It exposes endpoints for managing brand entities.
 *
 * Security: Only users with roles `ADMINISTRATOR` or `MANAGER` can access these endpoints.
 */
@RestController
@RequestMapping("/api/brand")
@RequiredArgsConstructor
public class BrandController {
    public final BrandService brandService;

    /**
     * Endpoint to get a list of all brands.
     *
     * @return ResponseEntity containing a wrapped list of BrandResponse objects.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<BrandResponse>>> getAllBrands() {
        List<BrandResponse> brandResponses = brandService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<BrandResponse>>builder()
                .message("Lista de marcas.")
                .data(brandResponses)
                .build());
    }

    /**
     * Endpoint to create a new brand.
     *
     * @param brandRequest the brand data to be saved.
     * @return ResponseEntity containing a wrapped BrandResponse object for the created brand.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<BrandResponse>> saveBrand(@Valid @RequestBody BrandRequest brandRequest) {
        BrandResponse brandResponse = brandService.save(brandRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.<BrandResponse>builder()
                .message("Marca creada exitosamente.")
                .data(brandResponse)
                .build());
    }

    /**
     * Endpoint to update an existing brand by its ID.
     *
     * @param id the ID of the brand to be updated.
     * @param brandRequest the updated brand data.
     * @return ResponseEntity containing a wrapped BrandResponse object for the updated brand.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<BrandResponse>> updateBrand(@PathVariable int id, @Valid @RequestBody BrandRequest brandRequest) {
        BrandResponse brandResponse = brandService.update(id, brandRequest);

        return ResponseEntity.ok(ResponseWrapper.<BrandResponse>builder()
                .message("Marca actualizada exitosamente.")
                .data(brandResponse)
                .build());
    }

    /**
     * Endpoint to delete a brand by its ID.
     *
     * @param id the ID of the brand to be deleted.
     * @return ResponseEntity containing a wrapped BrandResponse object for the deleted brand.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<BrandResponse>> deleteBrand(@PathVariable int id) {
        BrandResponse brandResponse = brandService.delete(id);

        return ResponseEntity.ok(ResponseWrapper.<BrandResponse>builder()
                .message("Marca eliminada.")
                .data(brandResponse)
                .build());
    }

}
