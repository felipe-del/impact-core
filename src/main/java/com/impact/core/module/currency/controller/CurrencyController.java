package com.impact.core.module.currency.controller;

import com.impact.core.module.currency.payload.response.CurrencyResponse;
import com.impact.core.module.currency.service.CurrencyService;
import com.impact.core.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/currency")
@RequiredArgsConstructor
public class CurrencyController {
    public final CurrencyService currencyService;

    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<List<CurrencyResponse>>> getAllCurrencies() {
        List<CurrencyResponse> currencyResponses = currencyService.findAll();

        return ResponseEntity.ok(ApiResponse.<List<CurrencyResponse>>builder()
                .message("Lista de monedas.")
                .data(currencyResponses)
                .build());
    }

}
