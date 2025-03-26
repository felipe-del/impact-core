package com.impact.core.module.productRequestStatistics.controller;

import com.impact.core.module.productRequestStatistics.payload.ProductRequestStatisticsByDateResponse;
import com.impact.core.module.productRequestStatistics.service.ProductRequestStatisticsByDateService;
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
@RequestMapping("/api/product-request-statistics")
@RequiredArgsConstructor
public class ProductRequestStatisticsByDateController {

    private final ProductRequestStatisticsByDateService productRequestStatisticsByDateService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<ProductRequestStatisticsByDateResponse>>> getAllProductRequestStatistics() {
        List<ProductRequestStatisticsByDateResponse> requestStatisticsResponses = productRequestStatisticsByDateService.findAll();

        return ResponseEntity.ok(ResponseWrapper.<List<ProductRequestStatisticsByDateResponse>>builder()
                .message("Lista de estadísticas de solicitudes de productos")
                .data(requestStatisticsResponses)
                .build());
    }

    @GetMapping("/range")
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ResponseWrapper<List<ProductRequestStatisticsByDateResponse>>>
    getAllProductRequestStatisticsInARange(@RequestParam("start_date") LocalDate startDate,
                                           @RequestParam("end_date") LocalDate endDate) {
        List<ProductRequestStatisticsByDateResponse> requestStatisticsByDateResponses =
                productRequestStatisticsByDateService.requestsStatisticsInARange(startDate, endDate);

        return ResponseEntity.ok(ResponseWrapper.<List<ProductRequestStatisticsByDateResponse>>builder()
                .message("Estadísticas de solicitudes entre" + startDate + " y " + endDate)
                .data(requestStatisticsByDateResponses)
                .build());
    }

}
