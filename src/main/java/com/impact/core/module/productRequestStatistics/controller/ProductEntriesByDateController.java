package com.impact.core.module.productRequestStatistics.controller;

import com.impact.core.module.productRequestStatistics.payload.ProductEntriesByDateResponse;
import com.impact.core.module.productRequestStatistics.service.ProductEntriesByDateService;
import com.impact.core.util.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/product-entries")
@RequiredArgsConstructor
public class ProductEntriesByDateController {
    public final ProductEntriesByDateService productEntriesByDateService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<ProductEntriesByDateResponse>>> getAllProductEntries() {
        List<ProductEntriesByDateResponse> productEntriesByDateResponses = productEntriesByDateService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<ProductEntriesByDateResponse>>builder()
                .message("Total de ingresos de productos por fechas")
                .data(productEntriesByDateResponses)
                .build());
    }

    @GetMapping("/range")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<ProductEntriesByDateResponse>>>
    getAllProductEntriesInARange(@RequestParam("start_date") LocalDate startDate,
                                 @RequestParam("end_date") LocalDate endDate) {
        List<ProductEntriesByDateResponse> productEntriesByDateResponses = productEntriesByDateService.entriesByDate(startDate, endDate);

        return ResponseEntity.ok(ResponseWrapper.<List<ProductEntriesByDateResponse>>builder()
                .message("Total de ingresos de productos entre " + startDate + " y " + endDate)
                .data(productEntriesByDateResponses)
                .build());
    }
}
