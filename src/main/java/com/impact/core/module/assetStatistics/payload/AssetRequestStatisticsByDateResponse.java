package com.impact.core.module.assetStatistics.payload;

import lombok.*;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) representing the response for asset request statistics by date.
 * This class contains the total number of asset requests and the corresponding request date.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssetRequestStatisticsByDateResponse {

    /**
     * The total number of asset requests made on a specific date.
     */
    private Long totalAssetRequests;

    /**
     * The date when the asset requests were made.
     */
    private LocalDate requestDate;
}
