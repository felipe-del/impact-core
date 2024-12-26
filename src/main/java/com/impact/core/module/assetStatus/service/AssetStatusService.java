package com.impact.core.module.assetStatus.service;

import com.impact.core.module.assetStatus.repository.AssetStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("assetStatusService")
@RequiredArgsConstructor
public class AssetStatusService {
    public final AssetStatusRepository assetStatusRepository;


}
