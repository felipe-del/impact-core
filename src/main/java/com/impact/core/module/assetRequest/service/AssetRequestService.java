package com.impact.core.module.assetRequest.service;

import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.asset.entity.Asset;
import com.impact.core.module.assetRequest.entity.AssetRequest;
import com.impact.core.module.assetRequest.mapper.AssetRequestMapper;
import com.impact.core.module.assetRequest.payload.request.AssetRequestDTORequest;
import com.impact.core.module.assetRequest.payload.response.AssetRequestDTOResponse;
import com.impact.core.module.assetRequest.repository.AssetRequestRepository;
import com.impact.core.module.assetStatus.enun.EAssetStatus;
import com.impact.core.module.assetStatus.service.AssetStatusService;
import com.impact.core.module.mail.factory.MailFactory;
import com.impact.core.module.mail.payload.ComposedMail;
import com.impact.core.module.mail.service.MailService;
import com.impact.core.module.user.entity.User;
import com.impact.core.module.user.service.UserService;
import com.impact.core.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("assetRequestService")
@RequiredArgsConstructor
public class AssetRequestService {
    public final AssetRequestRepository assetRequestRepository;
    public final AssetStatusService assetStatusService;
    public final AssetRequestMapper assetRequestMapper;
    public final UserService userService;
    public final MailService mailService;


    public AssetRequestDTOResponse save(UserDetailsImpl userDetails, AssetRequestDTORequest assetRequestDTORequest) {
        AssetRequest assetRequest = assetRequestMapper.toEntity(assetRequestDTORequest);
        Asset asset = assetRequest.getAsset();
        asset.setStatus(assetStatusService.findByName(EAssetStatus.ASSET_STATUS_EARRING));
        assetRequest.setAsset(asset);

        User user = userService.findById(userDetails.getId());
        assetRequest.setUser(user);
        AssetRequest assetRequestSaved = assetRequestRepository.save(assetRequest);

        ComposedMail composedMailToUser = MailFactory.createAssetRequestEmail(assetRequestSaved);
        mailService.sendComposedEmail(composedMailToUser);
        ComposedMail composedMailToAdmin = MailFactory.createAdminReviewAssetRequest(assetRequestSaved);
        mailService.sendComposedEmailToAllAdmins(composedMailToAdmin);

        return assetRequestMapper.toDTO(assetRequestSaved);
    }

    public AssetRequestDTOResponse update(int id, AssetRequestDTORequest assetRequestDTORequest) {
        AssetRequest assetRequest = this.findById(id);
        AssetRequest assetRequestUpdated = assetRequestMapper.toEntity(assetRequestDTORequest);
        assetRequestUpdated.setId(assetRequest.getId());
        assetRequestUpdated.setUser(assetRequest.getUser());
        assetRequestUpdated.setCreatedAt(assetRequest.getCreatedAt());
        AssetRequest assetRequestSaved = assetRequestRepository.save(assetRequestUpdated);
        return assetRequestMapper.toDTO(assetRequestSaved);
    }

    public void delete(int id) {
        AssetRequest assetRequest = findById(id);
        assetRequestRepository.delete(assetRequest);
    }

    public AssetRequest findById(int id) {
        return assetRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Solicitud con id: " + id + " no encontrada."));
    }

    public List<AssetRequestDTOResponse> findAll() {
        return assetRequestRepository.findAll().stream()
                .map(assetRequestMapper::toDTO)
                .collect(Collectors.toList());
    }
    public List<AssetRequestDTOResponse> findByUser(Integer user){
        return assetRequestRepository.assetsRequestByUser(user).stream()
                .map(assetRequestMapper::toDTO)
                .collect(Collectors.toList());
    }
    @Transactional
    public void updateStatus(Integer status, Integer productR){
        assetRequestRepository.updateAssetRequestStatus(status,productR);
    }


}
