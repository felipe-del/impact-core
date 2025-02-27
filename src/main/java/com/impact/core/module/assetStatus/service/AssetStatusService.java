package com.impact.core.module.assetStatus.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.assetStatus.entity.AssetStatus;
import com.impact.core.module.assetStatus.enun.EAssetStatus;
import com.impact.core.module.assetStatus.mapper.AssetStatusMapper;
import com.impact.core.module.assetStatus.payload.response.AssetStatusResponse;
import com.impact.core.module.assetStatus.repository.AssetStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("assetStatusService")
@RequiredArgsConstructor
public class AssetStatusService {
    public final AssetStatusRepository assetStatusRepository;
    public final AssetStatusMapper assetStatusMapper;

    public AssetStatus findByName(EAssetStatus name) {
        return assetStatusRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("El estado: " + name + " no existe en la base de datos."));
    }

    public List<AssetStatusResponse> findAll() {
        return assetStatusRepository.findAll().stream()
                .map(assetStatusMapper::toDTO)
                .collect(Collectors.toList());
    }

}
