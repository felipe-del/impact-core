package com.impact.core.module.assetStatistics.payload;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssetRequestStatisticsByDateResponse {
    private Long totalAssetRequests;
    private LocalDate requestDate;
}
