package com.impact.core.module.assetRequest.mapper;

import com.impact.core.module.asset.mapper.AssetMapper;
import com.impact.core.module.asset.service.AssetService;
import com.impact.core.module.assetRequest.entity.AssetRequest;
import com.impact.core.module.assetRequest.payload.renew.AssetRequestDTORenew;
import com.impact.core.module.assetRequest.payload.request.AssetRequestDTORequest;
import com.impact.core.module.assetRequest.payload.response.AssetRequestDTOResponse;
import com.impact.core.module.resource_request_status.enun.EResourceRequestStatus;
import com.impact.core.module.resource_request_status.mapper.ResourceRequestStatusMapper;
import com.impact.core.module.resource_request_status.service.ResourceRequestStatusService;
import com.impact.core.module.user.mapper.MyUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between AssetRequest entities and their corresponding DTOs.
 * <p>
 * This class contains methods to map AssetRequest data to and from DTOs (Data Transfer Objects).
 * It provides functionality for creating new AssetRequest entities from DTOs as well as converting
 * AssetRequest entities into DTOs that can be used in API responses.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class AssetRequestMapper {

    public final AssetService assetService;
    public final ResourceRequestStatusService resourceRequestStatusService;
    public final AssetMapper assetMapper;
    public final ResourceRequestStatusMapper resourceRequestStatusMapper;
    public final MyUserMapper myUserMapper;

    /**
     * Converts an AssetRequestDTORequest to an AssetRequest entity for creating a new request.
     * <p>
     * This method is used when creating a new AssetRequest. It maps the provided DTO fields to the
     * corresponding AssetRequest entity fields and assigns the appropriate status (e.g., "EARRING").
     * </p>
     *
     * @param assetRequestDTORequest the DTO containing data for creating an AssetRequest
     * @return the mapped AssetRequest entity
     */
    public AssetRequest toEntity(AssetRequestDTORequest assetRequestDTORequest) {
        return AssetRequest.builder()
                .asset(assetService.findById(assetRequestDTORequest.getAssetId()))
                .reason(assetRequestDTORequest.getReason())
                .expirationDate(assetRequestDTORequest.getExpirationDate())
                .status(resourceRequestStatusService.findByName(EResourceRequestStatus.RESOURCE_REQUEST_STATUS_EARRING))
                .build();
    }

    /**
     * Converts an AssetRequestDTORequest to an AssetRequest entity for renewing a request.
     * <p>
     * This method is used for processing a renewal request. It maps the DTO fields to the AssetRequest
     * entity and sets the status to "RENEWAL".
     * </p>
     *
     * @param assetRequestDTORequest the DTO containing renewal data for the AssetRequest
     * @return the mapped AssetRequest entity with the "RENEWAL" status
     */
    public AssetRequest toEntityRenewal(AssetRequestDTORequest assetRequestDTORequest) {
        return AssetRequest.builder()
                .asset(assetService.findById(assetRequestDTORequest.getAssetId()))
                .reason(assetRequestDTORequest.getReason())
                .expirationDate(assetRequestDTORequest.getExpirationDate())
                .status(resourceRequestStatusService.findByName(EResourceRequestStatus.RESOURCE_REQUEST_STATUS_RENEWAL))
                .build();
    }

    /**
     * Converts an AssetRequestDTORenew to an AssetRequest entity for a request awaiting renewal.
     * <p>
     * This method is used when an asset request is being marked as "WAITING_ON_RENEWAL", meaning it
     * is pending the renewal process. It converts the renewal DTO into an entity with the appropriate status.
     * </p>
     *
     * @param assetRequestDTORenew the DTO containing data for the request awaiting renewal
     * @return the mapped AssetRequest entity with the "WAITING_ON_RENEWAL" status
     */
    public AssetRequest toEntityWaitingRenewal(AssetRequestDTORenew assetRequestDTORenew) {
        return AssetRequest.builder()
                .asset(assetService.findById(assetRequestDTORenew.getAssetId()))
                .reason(assetRequestDTORenew.getReason())
                .expirationDate(assetRequestDTORenew.getExpirationDate())
                .status(resourceRequestStatusService.findByName(EResourceRequestStatus.RESOURCE_REQUEST_STATUS_WAITING_ON_RENEWAL))
                .build();
    }

    /**
     * Converts an AssetRequest entity to an AssetRequestDTOResponse for API response.
     * <p>
     * This method is used to map an AssetRequest entity into a DTO that can be returned in a response.
     * It includes information about the asset, request status, user, and other relevant fields.
     * </p>
     *
     * @param assetRequest the AssetRequest entity to be mapped to a DTO
     * @return the mapped AssetRequestDTOResponse
     */
    public AssetRequestDTOResponse toDTO(AssetRequest assetRequest) {
        return AssetRequestDTOResponse.builder()
                .id(assetRequest.getId())
                .asset(assetMapper.toDTO(assetRequest.getAsset()))
                .status(resourceRequestStatusMapper.toDTO(assetRequest.getStatus()))
                .reason(assetRequest.getReason())
                .expirationDate(assetRequest.getExpirationDate().toString())
                .user(myUserMapper.toDTO(assetRequest.getUser()))
                .createdAt(assetRequest.getCreatedAt().toString())
                .build();
    }
}
