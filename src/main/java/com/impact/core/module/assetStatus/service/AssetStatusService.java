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

/**
 * Service class responsible for handling business logic related to {@link AssetStatus}.
 * This service interacts with the {@link AssetStatusRepository} and {@link AssetStatusMapper} to perform operations on asset status data.
 */
@Service("assetStatusService")
@RequiredArgsConstructor
public class AssetStatusService {
    public final AssetStatusRepository assetStatusRepository;
    public final AssetStatusMapper assetStatusMapper;

    /**
     * Finds an {@link AssetStatus} by its name.
     *
     * @param name The {@link EAssetStatus} representing the asset status name.
     * @return The {@link AssetStatus} entity if found.
     * @throws ResourceNotFoundException If the asset status does not exist in the database.
     */
    public AssetStatus findByName(EAssetStatus name) {
        return assetStatusRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("El estado: " + name + " no existe en la base de datos."));
    }

    /**
     * Retrieves all asset statuses and maps them to a list of {@link AssetStatusResponse} DTOs.
     *
     * @return A list of {@link AssetStatusResponse} DTOs containing asset status data.
     */
    public List<AssetStatusResponse> findAll() {
        return assetStatusRepository.findAll().stream()
                .map(assetStatusMapper::toDTO)
                .collect(Collectors.toList());
    }

}
