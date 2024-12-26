package com.impact.core.module.assetModel.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.assetModel.entity.AssetModel;
import com.impact.core.module.assetModel.mapper.AssetModelMapper;
import com.impact.core.module.assetModel.payload.request.AssetModelRequest;
import com.impact.core.module.assetModel.payload.response.AssetModelResponse;
import com.impact.core.module.assetModel.repository.AssetModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("assetModelService")
@RequiredArgsConstructor
public class AssetModelService {
    public final AssetModelRepository assetModelRepository;
    public final AssetModelMapper assetModelMapper;

    public AssetModelResponse save(AssetModelRequest assetModelRequest) {
        AssetModel model = assetModelMapper.toEntity(assetModelRequest);
        AssetModel savedModel = assetModelRepository.save(model);
        return assetModelMapper.toDTO(model);
    }

    public AssetModelResponse update(int id, AssetModelRequest assetModelRequest) {
        AssetModel model = this.findById(id);
        AssetModel updatedModel = assetModelMapper.toEntity(assetModelRequest);
        updatedModel.setId(model.getId());
        AssetModel savedAssetModel =  assetModelRepository.save(updatedModel);
        return assetModelMapper.toDTO(savedAssetModel);
    }

    public AssetModelResponse delete(int id) {
        AssetModel model = this.findById(id);
        assetModelRepository.delete(model);
        return assetModelMapper.toDTO(model);
    }

    public AssetModel findById(int id) {
        return assetModelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Modelo con id " + id + " no encontrado."));
    }

    public List<AssetModelResponse> findAll() {
        return assetModelRepository.findAll().stream()
                .map(assetModelMapper::toDTO)
                .toList();
    }
}
