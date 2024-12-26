package com.impact.core.module.currency.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.currency.entity.Currency;
import com.impact.core.module.currency.enun.ECurrency;
import com.impact.core.module.currency.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("currencyService")
@RequiredArgsConstructor
public class CurrencyService {
    public final CurrencyRepository currencyRepository;

    public Currency findByCurrencyName(ECurrency currencyName) {
        return currencyRepository.findByCurrencyName(currencyName)
                .orElseThrow(() -> new ResourceNotFoundException("El tipo de moneda : " + currencyName + " no se encuentra en la base de datos"));
    }
}
