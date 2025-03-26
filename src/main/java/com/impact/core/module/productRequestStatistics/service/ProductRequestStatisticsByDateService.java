package com.impact.core.module.productRequestStatistics.service;

import com.impact.core.module.productRequestStatistics.mapper.ProductRequestStatisticsByDateMapper;
import com.impact.core.module.productRequestStatistics.payload.ProductRequestStatisticsByDateResponse;
import com.impact.core.module.productRequestStatistics.repository.ProductRequestStatisticsByDateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service("productRequestStatisticsByDateService")
@RequiredArgsConstructor
public class ProductRequestStatisticsByDateService {

    private final ProductRequestStatisticsByDateRepository productRequestStatisticsByDateRepository;
    private final ProductRequestStatisticsByDateMapper productRequestStatisticsByDateMapper;

    public List<ProductRequestStatisticsByDateResponse> findAll(){
        return productRequestStatisticsByDateRepository.findAll()
                .stream()
                .map(productRequestStatisticsByDateMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ProductRequestStatisticsByDateResponse> requestsStatisticsInARange(LocalDate startDate, LocalDate endDate) {
        return productRequestStatisticsByDateRepository.requestsInARange(startDate, endDate)
                .stream()
                .map(productRequestStatisticsByDateMapper::toDTO)
                .collect(Collectors.toList());
    }
}
