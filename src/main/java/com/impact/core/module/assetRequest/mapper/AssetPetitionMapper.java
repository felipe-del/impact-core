package com.impact.core.module.assetRequest.mapper;

import com.impact.core.module.asset.mapper.AssetMapper;
import com.impact.core.module.asset.service.AssetService;
import com.impact.core.module.assetRequest.entity.AssetPetition;
import com.impact.core.module.assetRequest.payload.request.AssetPetitionDTORequest;
import com.impact.core.module.assetRequest.payload.response.AssetPetitionDTOResponse;
import com.impact.core.module.resource_request_status.enun.EResourceRequestStatus;
import com.impact.core.module.resource_request_status.mapper.ResourceRequestStatusMapper;
import com.impact.core.module.resource_request_status.service.ResourceRequestStatusService;
import com.impact.core.module.user.mapper.MyUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssetPetitionMapper {

    public final AssetService assetService;
    public final ResourceRequestStatusService resourceRequestStatusService;
    public final AssetMapper assetMapper;
    public final ResourceRequestStatusMapper resourceRequestStatusMapper;
    public final MyUserMapper myUserMapper;

    public AssetPetition toEntity(AssetPetitionDTORequest assetPetitionDTORequest) {
        return AssetPetition.builder()
                .asset(assetService.findById(assetPetitionDTORequest.getAssetId()))
                .reason(assetPetitionDTORequest.getReason())
                .expirationDate(assetPetitionDTORequest.getExpirationDate())
                .status(resourceRequestStatusService.findByName(EResourceRequestStatus.RESOURCE_REQUEST_STATUS_EARRING))
                .build();
    }

    public AssetPetitionDTOResponse toDTO(AssetPetition assetPetition) {
        return AssetPetitionDTOResponse.builder()
                .id(assetPetition.getId())
                .asset(assetMapper.toDTO(assetPetition.getAsset()))
                .status(resourceRequestStatusMapper.toDTO(assetPetition.getStatus()))
                .reason(assetPetition.getReason())
                .expirationDate(assetPetition.getExpirationDate().toString())
                .user(myUserMapper.toDTO(assetPetition.getUser()))
                .createdAt(assetPetition.getCreatedAt().toString())
                .build();
    }
}
