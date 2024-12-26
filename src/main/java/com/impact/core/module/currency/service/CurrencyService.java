package com.impact.core.module.currency.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.currency.entity.Currency;
import com.impact.core.module.currency.enun.ECurrency;
import com.impact.core.module.currency.mapper.CurrencyMapper;
import com.impact.core.module.currency.payload.response.CurrencyResponse;
import com.impact.core.module.currency.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("currencyService")
@RequiredArgsConstructor
public class CurrencyService {
    public final CurrencyRepository currencyRepository;
    public final CurrencyMapper currencyMapper;

    public Currency findByCurrencyName(ECurrency currencyName) {
        return currencyRepository.findByCurrencyName(currencyName)
                .orElseThrow(() -> new ResourceNotFoundException("El tipo de moneda : " + currencyName + " no se encuentra en la base de datos"));
    }

    public List<CurrencyResponse> findAll() {
        return currencyRepository.findAll().stream()
                .map(currencyMapper::toDTO)
                .toList();
    }
}
