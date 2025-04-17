package com.impact.core.module.assetRequest.service;

import com.impact.core.expection.customException.ConflictException;
import com.impact.core.expection.customException.ResourceNotFoundException;
import com.impact.core.module.asset.entity.Asset;
import com.impact.core.module.asset.service.AssetService;
import com.impact.core.module.assetRequest.entity.AssetRequest;
import com.impact.core.module.assetRequest.mapper.AssetRequestMapper;
import com.impact.core.module.assetRequest.payload.renew.AssetRequestDTORenew;
import com.impact.core.module.assetRequest.payload.request.AssetRequestDTORequest;
import com.impact.core.module.assetRequest.payload.response.AssetRequestDTOResponse;
import com.impact.core.module.assetRequest.repository.AssetRequestRepository;
import com.impact.core.module.assetStatus.enun.EAssetStatus;
import com.impact.core.module.assetStatus.service.AssetStatusService;
import com.impact.core.module.mail.factory.MailFactory;
import com.impact.core.module.mail.payload.ComposedMail;
import com.impact.core.module.mail.service.MailService;
import com.impact.core.module.product.entity.Product;
import com.impact.core.module.productRequest.entity.ProductRequest;
import com.impact.core.module.productRequest.payload.response.ProductRequestDTOResponse;
import com.impact.core.module.productStatus.enun.EProductStatus;
import com.impact.core.module.resource_request_status.enun.EResourceRequestStatus;
import com.impact.core.module.resource_request_status.service.ResourceRequestStatusService;
import com.impact.core.module.schedule_task.service.DynamicSchedulerService;
import com.impact.core.module.user.entity.User;
import com.impact.core.module.user.service.UserService;
import com.impact.core.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service("assetRequestService")
@RequiredArgsConstructor
public class AssetRequestService {
    public final AssetRequestRepository assetRequestRepository;
    public final AssetStatusService assetStatusService;
    public final AssetRequestMapper assetRequestMapper;
    public final AssetService assetService;
    public final UserService userService;
    public final MailService mailService;
    private final DynamicSchedulerService dynamicSchedulerService;
    private final ResourceRequestStatusService resourceRequestStatusService;

    public AssetRequestDTOResponse save(UserDetailsImpl userDetails, AssetRequestDTORequest assetRequestDTORequest) {
        AssetRequest assetRequest = assetRequestMapper.toEntity(assetRequestDTORequest);
        Asset asset = assetRequest.getAsset();
        asset.setStatus(assetStatusService.findByName(EAssetStatus.ASSET_STATUS_EARRING));
        assetRequest.setAsset(asset);

        User user = userService.findById(userDetails.getId());
        assetRequest.setUser(user);

        AssetRequest assetRequestSaved = assetRequestRepository.save(assetRequest);
        dynamicSchedulerService.scheduleNotification(assetRequestSaved);

        ComposedMail composedMailToUser = MailFactory.createAssetRequestEmail(assetRequestSaved);
        mailService.sendComposedEmail(composedMailToUser);
        ComposedMail composedMailToAdmin = MailFactory.createAdminReviewAssetRequest(assetRequestSaved);
        mailService.sendComposedEmailToAllAdmins(composedMailToAdmin);

        return assetRequestMapper.toDTO(assetRequestSaved);
    }

    public AssetRequestDTOResponse saveRenew(UserDetailsImpl userDetails, AssetRequestDTORenew assetRequestDTORenew) {
        AssetRequest assetRequest = assetRequestMapper.toEntityWaitingRenewal(assetRequestDTORenew);
        Asset asset = assetRequest.getAsset();
        asset.setStatus(assetStatusService.findByName(EAssetStatus.ASSET_STATUS_EARRING));
        assetRequest.setAsset(asset);

        User user = userService.findById(userDetails.getId());
        assetRequest.setUser(user);

        AssetRequest assetRequestSaved = assetRequestRepository.save(assetRequest);
        dynamicSchedulerService.scheduleNotification(assetRequestSaved);

        ComposedMail composedMailToUser = MailFactory.createAssetRenewEmail(assetRequestSaved);
        mailService.sendComposedEmail(composedMailToUser);
        ComposedMail composedMailToAdmin = MailFactory.createAdminReviewAssetRenew(assetRequestSaved);
        mailService.sendComposedEmailToAllAdmins(composedMailToAdmin);

        return assetRequestMapper.toDTO(assetRequestSaved);
    }

    public AssetRequestDTOResponse update(int id, AssetRequestDTORequest assetRequestDTORequest) {
        return updateInternalLogic(id, assetRequestDTORequest, assetRequestMapper::toEntity);
    }

    public AssetRequestDTOResponse updateRenew(int id, AssetRequestDTORequest assetRequestDTORequest) {
        return updateInternalLogic(id, assetRequestDTORequest, assetRequestMapper::toEntityRenewal);
    }

    private AssetRequestDTOResponse updateInternalLogic(int id, AssetRequestDTORequest assetRequestDTORequest,
                                                        Function<AssetRequestDTORequest, AssetRequest> mappingFunction) {
        AssetRequest original = this.findById(id);
        AssetRequest updated = mappingFunction.apply(assetRequestDTORequest);

        updated.setId(original.getId());
        updated.setUser(original.getUser());
        updated.setCreatedAt(original.getCreatedAt());

        boolean expirationDateChanged = !original.getExpirationDate().equals(updated.getExpirationDate());
        AssetRequest saved = assetRequestRepository.save(updated);

        if (expirationDateChanged) {
            dynamicSchedulerService.scheduleNotification(updated);
        }

        return assetRequestMapper.toDTO(saved);
    }

    public void delete(int id) {
        AssetRequest assetRequest = findById(id);
        assetRequestRepository.delete(assetRequest);
    }

    public AssetRequest findById(int id) {
        return assetRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Solicitud con id: " + id + " no encontrada."));
    }

    public AssetRequestDTOResponse findByIdDTO(int id) {
        AssetRequest assetRequest = findById(id);
        return assetRequestMapper.toDTO(assetRequest);
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
    public void updateStatus(Integer status, Integer assetRequestId, String cancelReason){
        AssetRequest assetRequest = findById(assetRequestId);

        ComposedMail composedMailToUser = MailFactory.composeUserNotificationCancelAssetRequest(assetRequest, cancelReason);
        mailService.sendComposedEmail(composedMailToUser);
        ComposedMail composedMailToAdmin = MailFactory.composeAdminNotificationCancelAssetRequest(assetRequest, cancelReason);
        mailService.sendComposedEmailToAllAdmins(composedMailToAdmin);

        assetRequestRepository.updateAssetRequestStatus(status, assetRequestId);
    }

    @Transactional
    public void updateRenewalStatusAccepted(Integer assetRequestId){
        AssetRequest originalAssetRequest = findById(assetRequestId);
        AssetRequest renewedAssetRequest = getValidatedRenewedRequest(originalAssetRequest);

        assetRequestRepository.updateAssetRequestStatus(
                resourceRequestStatusService.findByName(EResourceRequestStatus.RESOURCE_REQUEST_STATUS_ACCEPTED).getId(),
                renewedAssetRequest.getId());

        assetRequestRepository.updateAssetRequestStatus(
                resourceRequestStatusService.findByName(EResourceRequestStatus.RESOURCE_REQUEST_STATUS_CANCELED).getId(),
                originalAssetRequest.getId());

        ComposedMail composedMailToUser = MailFactory.composeUserNotificationAcceptAssetRenewalRequest(renewedAssetRequest);
        mailService.sendComposedEmail(composedMailToUser);
    }

    @Transactional
    public void updateRenewalStatusRejected(Integer assetRequestId){
        AssetRequest originalAssetRequest = findById(assetRequestId);
        AssetRequest rejectingRenewedAssetRequest = getValidatedRenewedRequest(originalAssetRequest);

        assetRequestRepository.updateAssetRequestStatus(
                resourceRequestStatusService.findByName(EResourceRequestStatus.RESOURCE_REQUEST_STATUS_CANCELED).getId(),
                rejectingRenewedAssetRequest.getId());

        assetRequestRepository.updateAssetRequestStatus(
                resourceRequestStatusService.findByName(EResourceRequestStatus.RESOURCE_REQUEST_STATUS_ACCEPTED).getId(),
                originalAssetRequest.getId());

        ComposedMail composedMailToUser = MailFactory.composeUserNotificationRejectAssetRenewalRequest(rejectingRenewedAssetRequest);
        mailService.sendComposedEmail(composedMailToUser);
    }

    private AssetRequest getValidatedRenewedRequest(AssetRequest originalAssetRequest){
        Asset asset = assetService.findById(originalAssetRequest.getAsset().getId());

        AssetRequest renewedAssetRequest = findWaitingOnRenewal(asset.getPlateNumber(),
                originalAssetRequest.getReason(),
                originalAssetRequest.getUser().getId());

        asset.setStatus(assetStatusService.findByName(EAssetStatus.ASSET_STATUS_LOANED));

        if (renewedAssetRequest == null) {
            throw new ResourceNotFoundException("No existe una solicitud en espera de renovación de otra solicitud para el activo con placa: "
                    + asset.getPlateNumber());
        }

        return renewedAssetRequest;
    }

    public List<AssetRequest> findByPending() {
        return assetRequestRepository.assetsRequestByStatus(1); //status 1-> RESOURCE_REQUEST_STATUS_EARRING
    }

    public List<AssetRequestDTOResponse> findAllRenewal() {
        return assetRequestRepository.assetsRequestByStatus(
                resourceRequestStatusService.findByName(EResourceRequestStatus.RESOURCE_REQUEST_STATUS_RENEWAL).getId())
                .stream()
                .map(assetRequestMapper::toDTO)
                .collect(Collectors.toList());
    }

    private AssetRequest findWaitingOnRenewal(String plateNumber, String reason, Integer userId) {
        for(AssetRequest a : assetRequestRepository.assetsRequestByStatus(resourceRequestStatusService.findByName(
                EResourceRequestStatus.RESOURCE_REQUEST_STATUS_WAITING_ON_RENEWAL).getId())){
            if(a.getAsset().getPlateNumber().equals(plateNumber) && a.getReason().equals(reason) && a.getUser().getId().equals(userId)){
                return a;
            }
        }
        return null;
    }

    public List<AssetRequestDTOResponse> findAllExcludingEarringAndRenewal() {
        List<AssetRequest> allRequests = assetRequestRepository.findAll();

        return allRequests.stream()
                .filter(request -> {
                    EResourceRequestStatus statusEnum = request.getStatus().getName();
                    return statusEnum != EResourceRequestStatus.RESOURCE_REQUEST_STATUS_EARRING &&
                            statusEnum != EResourceRequestStatus.RESOURCE_REQUEST_STATUS_RENEWAL;
                })
                .map(assetRequestMapper::toDTO)
                .toList();
    }

    public List<AssetRequestDTOResponse> findAllWithEarring() {
        List<AssetRequest> allRequests = assetRequestRepository.findAll();

        return allRequests.stream()
                .filter(request -> request.getStatus().getName() == EResourceRequestStatus.RESOURCE_REQUEST_STATUS_EARRING)
                .map(assetRequestMapper::toDTO)
                .collect(Collectors.toList());
    }

    public AssetRequestDTOResponse acceptRequest(Integer assetRequestId){
        AssetRequest assetRequest = findById(assetRequestId);
        if(assetRequest.getStatus().getName() != EResourceRequestStatus.RESOURCE_REQUEST_STATUS_EARRING){
            throw new ConflictException("La solicitud de activo con el id: " + assetRequestId + " no está en espera.");
        }
        assetRequest.setStatus(
                resourceRequestStatusService.findByName(EResourceRequestStatus.RESOURCE_REQUEST_STATUS_ACCEPTED)
        );
        Asset asset = assetRequest.getAsset();
        asset.setStatus(
                assetStatusService.findByName(EAssetStatus.ASSET_STATUS_LOANED)
        );
        AssetRequest saved = assetRequestRepository.save(assetRequest);

        ComposedMail composedMailToUser = MailFactory.createAssetRequestAcceptedEmail(assetRequest);
        mailService.sendComposedEmail(composedMailToUser);

        return assetRequestMapper.toDTO(saved);
    }

}
