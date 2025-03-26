package com.impact.core.module.productRequestStatistics.service;

import com.impact.core.module.productRequestStatistics.mapper.ProductEntriesByDateMapper;
import com.impact.core.module.productRequestStatistics.payload.ProductEntriesByDateResponse;
import com.impact.core.module.productRequestStatistics.repository.ProductEntriesByDateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service("productEntriesByDateService")
@RequiredArgsConstructor
public class ProductEntriesByDateService {

    public final ProductEntriesByDateRepository productEntriesByDateRepository;
    public final ProductEntriesByDateMapper  productEntriesByDateMapper;

    public List<ProductEntriesByDateResponse> findAll() {
        return productEntriesByDateRepository.findAll()
                .stream()
                .map(productEntriesByDateMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ProductEntriesByDateResponse> entriesByDate(LocalDate startDate, LocalDate endDate) {
        return productEntriesByDateRepository.entriesInARange(startDate, endDate)
                .stream()
                .map(productEntriesByDateMapper::toDTO)
                .collect(Collectors.toList());
    }
}
