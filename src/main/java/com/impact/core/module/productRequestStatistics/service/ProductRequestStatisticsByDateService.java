package com.impact.core.module.productRequestStatistics.service;

import com.impact.core.module.productRequestStatistics.mapper.ProductRequestStatisticsByDateMapper;
import com.impact.core.module.productRequestStatistics.payload.ProductRequestStatisticsByDateResponse;
import com.impact.core.module.productRequestStatistics.repository.ProductRequestStatisticsByDateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing product request statistics by date.
 * <p>
 * This service handles the logic for retrieving and processing product request statistics by date. It interacts with
 * the repository {@link ProductRequestStatisticsByDateRepository} to fetch data and uses {@link ProductRequestStatisticsByDateMapper}
 * to convert entities to Data Transfer Objects (DTOs) for easier consumption by clients.
 * </p>
 */
@Service("productRequestStatisticsByDateService")
@RequiredArgsConstructor
public class ProductRequestStatisticsByDateService {

    private final ProductRequestStatisticsByDateRepository productRequestStatisticsByDateRepository;
    private final ProductRequestStatisticsByDateMapper productRequestStatisticsByDateMapper;

    /**
     * Retrieves all product request statistics by date and converts them to DTOs.
     * <p>
     * This method fetches all product request statistics from the database and converts each entity to a
     * {@link ProductRequestStatisticsByDateResponse} DTO using {@link ProductRequestStatisticsByDateMapper}.
     * </p>
     *
     * @return a list of {@link ProductRequestStatisticsByDateResponse} DTOs representing the product request statistics.
     */
    public List<ProductRequestStatisticsByDateResponse> findAll(){
        return productRequestStatisticsByDateRepository.findAll()
                .stream()
                .map(productRequestStatisticsByDateMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves product request statistics by date in a specific date range and converts them to DTOs.
     * <p>
     * This method fetches product request statistics within the specified date range and converts each entity
     * to a {@link ProductRequestStatisticsByDateResponse} DTO using {@link ProductRequestStatisticsByDateMapper}.
     * </p>
     *
     * @param startDate the start date of the range.
     * @param endDate the end date of the range.
     * @return a list of {@link ProductRequestStatisticsByDateResponse} DTOs representing the product request statistics
     *         within the specified date range.
     */
    public List<ProductRequestStatisticsByDateResponse> requestsStatisticsInARange(LocalDate startDate, LocalDate endDate) {
        return productRequestStatisticsByDateRepository.requestsInARange(startDate, endDate)
                .stream()
                .map(productRequestStatisticsByDateMapper::toDTO)
                .collect(Collectors.toList());
    }
}
