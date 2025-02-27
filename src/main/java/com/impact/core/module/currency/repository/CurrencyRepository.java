package com.impact.core.module.currency.repository;

import com.impact.core.module.currency.entity.Currency;
import com.impact.core.module.currency.enun.ECurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    Optional<Currency> findByCurrencyName(ECurrency currencyName);
}
