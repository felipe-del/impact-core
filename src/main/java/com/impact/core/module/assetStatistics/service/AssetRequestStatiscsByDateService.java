package com.impact.core.module.assetStatistics.service;

import com.impact.core.module.assetStatistics.entity.AssetRequestStatisticsByDate;
import com.impact.core.module.assetStatistics.repository.AssetRequestStatisticsByDateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class that provides methods to interact with asset request statistics by date.
 * This class handles the business logic and acts as a bridge between the repository and the controller.
 */
@Service("assetRequestStatisticsByDateService")
@RequiredArgsConstructor
public class AssetRequestStatiscsByDateService {

    /**
     * The repository for asset request statistics by date.
     * It is used to interact with the underlying database or view.
     */
    private final AssetRequestStatisticsByDateRepository assetRequestStatisticsByDateRepository;

    /**
     * Retrieves all asset request statistics by date from the database.
     * This method queries the {@link AssetRequestStatisticsByDateRepository} for all records.
     *
     * @return A list of {@link AssetRequestStatisticsByDate} containing the statistics of asset requests.
     */
    public List<AssetRequestStatisticsByDate> findAll(){
        return assetRequestStatisticsByDateRepository.findAll();
    }

}
