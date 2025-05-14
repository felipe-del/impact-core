package com.impact.core.module.currency.mapper;

import com.impact.core.module.currency.entity.Currency;
import com.impact.core.module.currency.enun.ECurrency;
import com.impact.core.module.currency.payload.response.CurrencyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


/**
 * Mapper class responsible for converting between Currency entity and
 * its corresponding Data Transfer Object (DTO) representation.
 */
@Component
public class CurrencyMapper {

    /**
     * Converts a Currency entity to its corresponding CurrencyResponse
     * Data Transfer Object (DTO).
     * <p>
     * Adds the appropriate currency code and symbol depending on the
     * currency name enumeration value.
     *
     * @param currency The Currency entity to convert.
     * @return A CurrencyResponse Data Transfer Object (DTO) with mapped fields.
     */
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
