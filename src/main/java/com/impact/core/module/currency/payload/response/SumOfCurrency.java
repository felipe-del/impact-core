package com.impact.core.module.currency.payload.response;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SumOfCurrency {
    private CurrencyResponse currency;
    private BigDecimal amount;
}
