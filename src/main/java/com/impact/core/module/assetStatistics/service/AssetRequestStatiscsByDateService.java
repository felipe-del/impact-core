package com.impact.core.module.assetStatistics.service;

import com.impact.core.module.assetStatistics.entity.AssetRequestStatisticsByDate;
import com.impact.core.module.assetStatistics.repository.AssetRequestStatisticsByDateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("assetRequestStatisticsByDateService")
@RequiredArgsConstructor
public class AssetRequestStatiscsByDateService {

    private final AssetRequestStatisticsByDateRepository assetRequestStatisticsByDateRepository;

    public List<AssetRequestStatisticsByDate> findAll(){
        return assetRequestStatisticsByDateRepository.findAll();
    }

}
