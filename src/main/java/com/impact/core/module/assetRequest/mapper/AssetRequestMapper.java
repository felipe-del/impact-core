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

@Component
@RequiredArgsConstructor
public class AssetRequestMapper {

    public final AssetService assetService;
    public final ResourceRequestStatusService resourceRequestStatusService;
    public final AssetMapper assetMapper;
    public final ResourceRequestStatusMapper resourceRequestStatusMapper;
    public final MyUserMapper myUserMapper;

    public AssetRequest toEntity(AssetRequestDTORequest assetRequestDTORequest) {
        return AssetRequest.builder()
                .asset(assetService.findById(assetRequestDTORequest.getAssetId()))
                .reason(assetRequestDTORequest.getReason())
                .expirationDate(assetRequestDTORequest.getExpirationDate())
                .status(resourceRequestStatusService.findByName(EResourceRequestStatus.RESOURCE_REQUEST_STATUS_EARRING))
                .build();
    }

    public AssetRequest toEntityRenewal(AssetRequestDTORequest assetRequestDTORequest) {
        return AssetRequest.builder()
                .asset(assetService.findById(assetRequestDTORequest.getAssetId()))
                .reason(assetRequestDTORequest.getReason())
                .expirationDate(assetRequestDTORequest.getExpirationDate())
                .status(resourceRequestStatusService.findByName(EResourceRequestStatus.RESOURCE_REQUEST_STATUS_RENEWAL))
                .build();
    }

    public AssetRequest toEntityWaitingRenewal(AssetRequestDTORenew assetRequestDTORenew) {
        return AssetRequest.builder()
                .asset(assetService.findById(assetRequestDTORenew.getAssetId()))
                .reason(assetRequestDTORenew.getReason())
                .expirationDate(assetRequestDTORenew.getExpirationDate())
                .status(resourceRequestStatusService.findByName(EResourceRequestStatus.RESOURCE_REQUEST_STATUS_WAITING_ON_RENEWAL))
                .build();
    }

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
