package com.impact.core.module.brand.controller;

import com.impact.core.module.brand.payload.request.BrandRequest;
import com.impact.core.module.brand.payload.response.BrandResponse;
import com.impact.core.module.brand.service.BrandService;
import com.impact.core.util.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brand")
@RequiredArgsConstructor
public class BrandController {
    public final BrandService brandService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER') or hasRole('USER')")
    public ResponseEntity<ApiResponse<List<BrandResponse>>> getAllBrands() {
        List<BrandResponse> brandResponses = brandService.findAll();

        return ResponseEntity.ok(ApiResponse.<List<BrandResponse>>builder()
                .message("Lista de marcas.")
                .data(brandResponses)
                .build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<BrandResponse>> saveBrand(@Valid @RequestBody BrandRequest brandRequest) {
        BrandResponse brandResponse = brandService.save(brandRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<BrandResponse>builder()
                .message("Marca creada exitosamente.")
                .data(brandResponse)
                .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<BrandResponse>> updateBrand(@PathVariable int id, @Valid @RequestBody BrandRequest brandRequest) {
        BrandResponse brandResponse = brandService.update(id, brandRequest);

        return ResponseEntity.ok(ApiResponse.<BrandResponse>builder()
                .message("Marca actualizada exitosamente.")
                .data(brandResponse)
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<BrandResponse>> deleteBrand(@PathVariable int id) {
        BrandResponse brandResponse = brandService.delete(id);

        return ResponseEntity.ok(ApiResponse.<BrandResponse>builder()
                .message("Marca eliminada.")
                .data(brandResponse)
                .build());
    }

}
