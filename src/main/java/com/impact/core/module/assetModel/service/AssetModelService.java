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

/**
 * Service class for managing operations related to {@link AssetModel} entities.
 * <p>
 * This service provides business logic for handling asset model operations, such as creating, updating, deleting, and retrieving asset models.
 * It interacts with the {@link AssetModelRepository} to perform CRUD operations and uses the {@link AssetModelMapper} to map between entities and their respective Data Transfer Objects (DTOs).
 * </p>
 */
@Service("assetModelService")
@RequiredArgsConstructor
public class AssetModelService {
    public final AssetModelRepository assetModelRepository;
    public final AssetModelMapper assetModelMapper;

   /**
    * Saves a new asset model.
    * <p>
    * This method converts the {@link AssetModelRequest} into an {@link AssetModel} entity, saves it to the repository, and returns the saved asset model as a {@link AssetModelResponse} DTO.
    * </p>
    * @param assetModelRequest the {@link AssetModelRequest} containing the data to create the new asset model
    * @return {@link AssetModelResponse} containing the details of the saved asset model
    */
    public AssetModelResponse save(AssetModelRequest assetModelRequest) {
        AssetModel model = assetModelMapper.toEntity(assetModelRequest);
        AssetModel savedModel = assetModelRepository.save(model);
        return assetModelMapper.toDTO(model);
    }

   /**
    * Updates an existing asset model by its ID.
    * <p>
    * This method retrieves the existing asset model by ID, updates it with the provided {@link AssetModelRequest}, and returns the updated model as an {@link AssetModelResponse} DTO.
    * </p>
    * @param id the ID of the asset model to be updated
    * @param assetModelRequest the {@link AssetModelRequest} containing the updated data for the asset model
    * @return {@link AssetModelResponse} containing the updated asset model details
    */
    public AssetModelResponse update(int id, AssetModelRequest assetModelRequest) {
        AssetModel model = this.findById(id);
        AssetModel updatedModel = assetModelMapper.toEntity(assetModelRequest);
        updatedModel.setId(model.getId());
        AssetModel savedAssetModel =  assetModelRepository.save(updatedModel);
        return assetModelMapper.toDTO(savedAssetModel);
    }

   /**
    * Deletes an asset model by its ID.
    * <p>
    * This method deletes the asset model with the specified ID and returns the deleted model as a {@link AssetModelResponse} DTO.
    * </p>
    * @param id the ID of the asset model to be deleted
    * @return {@link AssetModelResponse} containing the details of the deleted asset model
    */
    public AssetModelResponse delete(int id) {
        AssetModel model = this.findById(id);
        assetModelRepository.delete(model);
        return assetModelMapper.toDTO(model);
    }

   /**
    * Finds an asset model by its ID.
    * <p>
    * This method retrieves an {@link AssetModel} entity from the repository by its ID. If the asset model is not found, it throws a {@link ResourceNotFoundException}.
    * </p>
    * @param id the ID of the asset model to be retrieved
    * @return the found {@link AssetModel} entity
    */
    public AssetModel findById(int id) {
        return assetModelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Modelo con id " + id + " no encontrado."));
    }

   /**
    * Retrieves all asset models.
    * <p>
    * This method retrieves all asset models from the repository and converts them into a list of {@link AssetModelResponse} DTOs.
    * </p>
    * @return a list of {@link AssetModelResponse} containing the details of all asset models
    */
    public List<AssetModelResponse> findAll() {
        return assetModelRepository.findAll().stream()
                .map(assetModelMapper::toDTO)
                .toList();
    }
}
