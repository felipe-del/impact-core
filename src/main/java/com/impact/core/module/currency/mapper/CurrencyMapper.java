package com.impact.core.module.currency.mapper;

import com.impact.core.module.currency.entity.Currency;
import com.impact.core.module.currency.enun.ECurrency;
import com.impact.core.module.currency.payload.response.CurrencyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class CurrencyMapper {
    public CurrencyResponse toDTO(Currency currency) {
        String code = "-", symbol = "-";
        if (currency.getCurrencyName().equals(ECurrency.CURRENCY_COLON)) {
            code = "COL";
            symbol = "₡";
        } else if (currency.getCurrencyName().equals(ECurrency.CURRENCY_DOLLAR)) {
            code = "USD";
            symbol = "$";
        }
        return CurrencyResponse.builder()
                .id(currency.getId())
                .code(code)
                .symbol(symbol)
                .stateName(currency.getCurrencyName().toString())
                .build();
    }
}
