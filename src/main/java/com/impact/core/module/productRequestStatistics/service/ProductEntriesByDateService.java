package com.impact.core.module.productRequestStatistics.service;

import com.impact.core.module.productRequestStatistics.entity.ProductEntriesByDate;
import com.impact.core.module.productRequestStatistics.mapper.ProductEntriesByDateMapper;
import com.impact.core.module.productRequestStatistics.payload.ProductEntriesByDateResponse;
import com.impact.core.module.productRequestStatistics.repository.ProductEntriesByDateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for handling operations related to {@link ProductEntriesByDate} entities.
 * <p>
 * This service provides methods to retrieve and process product entries by date, either by fetching all entries or
 * filtering them based on a specified date range.
 * </p>
 */
@Service("productEntriesByDateService")
@RequiredArgsConstructor
public class ProductEntriesByDateService {

    public final ProductEntriesByDateRepository productEntriesByDateRepository;
    public final ProductEntriesByDateMapper  productEntriesByDateMapper;

    /**
     * Retrieves all product entries by date.
     * <p>
     * This method fetches all product entries from the repository and maps them to {@link ProductEntriesByDateResponse} DTOs.
     * </p>
     *
     * @return a list of {@link ProductEntriesByDateResponse} containing all product entries.
     */
    public List<ProductEntriesByDateResponse> findAll() {
        return productEntriesByDateRepository.findAll()
                .stream()
                .map(productEntriesByDateMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves product entries within a specified date range.
     * <p>
     * This method filters product entries based on the given start and end dates and maps the results to {@link ProductEntriesByDateResponse} DTOs.
     * </p>
     *
     * @param startDate the start date of the range.
     * @param endDate   the end date of the range.
     * @return a list of {@link ProductEntriesByDateResponse} containing product entries within the specified date range.
     */
    public List<ProductEntriesByDateResponse> entriesByDate(LocalDate startDate, LocalDate endDate) {
        return productEntriesByDateRepository.entriesInARange(startDate, endDate)
                .stream()
                .map(productEntriesByDateMapper::toDTO)
                .collect(Collectors.toList());
    }
}
