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

/**
 * Service class for managing asset requests, including creation, updates, and deletion of asset requests.
 * This class handles the business logic related to asset requests, including the status management and
 * communication with the mail system to notify users and admins.
 * <p>
 * It interacts with various services such as {@link AssetStatusService}, {@link UserService}, {@link MailService},
 * and {@link ResourceRequestStatusService} for managing asset requests and their states.
 * </p>
 */
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

    /**
     * Saves a new asset request based on the provided {@link AssetRequestDTORequest} and sends email notifications.
     *
     * @param userDetails        The details of the user making the request.
     * @param assetRequestDTORequest The data transfer object containing the asset request data.
     * @return A {@link AssetRequestDTOResponse} representing the saved asset request.
     */
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

    /**
     * Saves a renewed asset request based on the provided {@link AssetRequestDTORenew} and sends email notifications.
     *
     * @param userDetails        The details of the user making the request.
     * @param assetRequestDTORenew The data transfer object containing the asset renewal request data.
     * @return A {@link AssetRequestDTOResponse} representing the saved renewed asset request.
     */
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

    /**
     * Updates an existing asset request based on the provided {@link AssetRequestDTORequest}.
     *
     * @param id                 The ID of the asset request to update.
     * @param assetRequestDTORequest The data transfer object containing the updated asset request data.
     * @return A {@link AssetRequestDTOResponse} representing the updated asset request.
     */
    public AssetRequestDTOResponse update(int id, AssetRequestDTORequest assetRequestDTORequest) {
        return updateInternalLogic(id, assetRequestDTORequest, assetRequestMapper::toEntity);
    }

    /**
     * Updates an existing asset request for renewal based on the provided {@link AssetRequestDTORequest}.
     *
     * @param id                 The ID of the asset request to update.
     * @param assetRequestDTORequest The data transfer object containing the updated renewal asset request data.
     * @return A {@link AssetRequestDTOResponse} representing the updated renewal asset request.
     */
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

    /**
     * Deletes an asset request by its ID.
     *
     * @param id The ID of the asset request to delete.
     */
    public void delete(int id) {
        AssetRequest assetRequest = findById(id);
        assetRequestRepository.delete(assetRequest);
    }

    /**
     * Finds an asset request by its ID.
     *
     * @param id The ID of the asset request to find.
     * @return The {@link AssetRequest} entity associated with the given ID.
     * @throws ResourceNotFoundException If the asset request with the given ID is not found.
     */
    public AssetRequest findById(int id) {
        return assetRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Solicitud con id: " + id + " no encontrada."));
    }

    /**
     * Finds an asset request by its ID and returns it as a {@link AssetRequestDTOResponse}.
     *
     * @param id The ID of the asset request to find.
     * @return A {@link AssetRequestDTOResponse} representing the asset request.
     */
    public AssetRequestDTOResponse findByIdDTO(int id) {
        AssetRequest assetRequest = findById(id);
        return assetRequestMapper.toDTO(assetRequest);
    }

    /**
     * Retrieves all asset requests as a list of {@link AssetRequestDTOResponse}.
     *
     * @return A list of {@link AssetRequestDTOResponse} representing all asset requests.
     */
    public List<AssetRequestDTOResponse> findAll() {
        return assetRequestRepository.findAll().stream()
                .map(assetRequestMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves asset requests by user ID and returns them as a list of {@link AssetRequestDTOResponse}.
     *
     * @param user The user ID to filter asset requests by.
     * @return A list of {@link AssetRequestDTOResponse} representing the asset requests for the given user.
     */
    public List<AssetRequestDTOResponse> findByUser(Integer user){
        return assetRequestRepository.assetsRequestByUser(user).stream()
                .map(assetRequestMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Updates the status of an asset request, sending notifications to the user and admins.
     *
     * @param status           The new status for the asset request.
     * @param assetRequestId   The ID of the asset request to update.
     * @param cancelReason     The reason for the cancellation.
     */
    @Transactional
    public void updateStatus(Integer status, Integer assetRequestId, String cancelReason){
        AssetRequest assetRequest = findById(assetRequestId);

        ComposedMail composedMailToUser = MailFactory.composeUserNotificationCancelAssetRequest(assetRequest, cancelReason);
        mailService.sendComposedEmail(composedMailToUser);
        ComposedMail composedMailToAdmin = MailFactory.composeAdminNotificationCancelAssetRequest(assetRequest, cancelReason);
        mailService.sendComposedEmailToAllAdmins(composedMailToAdmin);

        assetRequestRepository.updateAssetRequestStatus(status, assetRequestId);
    }

    /**
     * Updates the renewal status of an asset request to "accepted" and sends a notification to the user.
     *
     * @param assetRequestId   The ID of the asset request to update.
     */
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

    /**
     * Updates the renewal status of an asset request to "rejected" and sends a notification to the user.
     *
     * @param assetRequestId   The ID of the asset request to update.
     */
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

    /**
     * Validates the renewed asset request based on the original asset request.
     *
     * @param originalAssetRequest The original asset request.
     * @return The validated renewed asset request.
     * @throws ResourceNotFoundException If the renewed asset request cannot be found.
     */
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

    /**
     * Retrieves a list of asset requests that are pending approval.
     *
     * @return A list of {@link AssetRequest} representing pending asset requests.
     */
    public List<AssetRequest> findByPending() {
        return assetRequestRepository.assetsRequestByStatus(1); //status 1-> RESOURCE_REQUEST_STATUS_EARRING
    }

    /**
     * Retrieves a list of asset requests that are in the renewal status, excluding "earring" and "renewal" statuses.
     *
     * @return A list of {@link AssetRequestDTOResponse} representing asset requests in the renewal status.
     */
    public List<AssetRequestDTOResponse> findAllRenewal() {
        return assetRequestRepository.assetsRequestByStatus(
                resourceRequestStatusService.findByName(EResourceRequestStatus.RESOURCE_REQUEST_STATUS_RENEWAL).getId())
                .stream()
                .map(assetRequestMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Finds an asset request that is waiting for renewal based on the plate number, reason, and user ID.
     *
     * @param plateNumber The plate number of the asset.
     * @param reason      The reason for the asset request.
     * @param userId      The ID of the user who made the request.
     * @return The {@link AssetRequest} entity that is waiting for renewal, or null if not found.
     */
    private AssetRequest findWaitingOnRenewal(String plateNumber, String reason, Integer userId) {
        for(AssetRequest a : assetRequestRepository.assetsRequestByStatus(resourceRequestStatusService.findByName(
                EResourceRequestStatus.RESOURCE_REQUEST_STATUS_WAITING_ON_RENEWAL).getId())){
            if(a.getAsset().getPlateNumber().equals(plateNumber) && a.getReason().equals(reason) && a.getUser().getId().equals(userId)){
                return a;
            }
        }
        return null;
    }

    /**
     * Retrieves a list of asset requests excluding "earring" and "renewal" statuses.
     *
     * @return A list of {@link AssetRequestDTOResponse} representing asset requests excluding "earring" and "renewal".
     */
    public List<AssetRequestDTOResponse> findAllExcludingEarringAndRenewal() {
        List<AssetRequest> allRequests = assetRequestRepository.findAll();

        return allRequests.stream()
                .filter(request -> {
                    EResourceRequestStatus statusEnum = request.getStatus().getName();
                    return statusEnum != EResourceRequestStatus.RESOURCE_REQUEST_STATUS_EARRING &&
                            statusEnum != EResourceRequestStatus.RESOURCE_REQUEST_STATUS_RENEWAL &&
                            statusEnum != EResourceRequestStatus.RESOURCE_REQUEST_STATUS_WAITING_ON_RENEWAL;
                })
                .map(assetRequestMapper::toDTO)
                .toList();
    }

    /**
     * Retrieves a list of asset requests that are in the "earring" status.
     *
     * @return A list of {@link AssetRequestDTOResponse} representing asset requests in the "earring" status.
     */
    public List<AssetRequestDTOResponse> findAllWithEarring() {
        List<AssetRequest> allRequests = assetRequestRepository.findAll();

        return allRequests.stream()
                .filter(request -> request.getStatus().getName() == EResourceRequestStatus.RESOURCE_REQUEST_STATUS_EARRING)
                .map(assetRequestMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Accepts an asset request, changing its status to "accepted" and updating the asset status to "loaned".
     *
     * @param assetRequestId The ID of the asset request to accept.
     * @return A {@link AssetRequestDTOResponse} representing the accepted asset request.
     * @throws ConflictException If the asset request is not in the "earring" status.
     */
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

    /**
     * Rejects an asset request, changing its status to "canceled" and updating the asset status to "available".
     *
     * @param assetRequestId The ID of the asset request to reject.
     * @return A {@link AssetRequestDTOResponse} representing the rejected asset request.
     * @throws ConflictException If the asset request is already accepted or canceled.
     */
    public AssetRequestDTOResponse rejectRequest(Integer assetRequestId){
        AssetRequest assetRequest = findById(assetRequestId);
        if(assetRequest.getStatus().getName() == EResourceRequestStatus.RESOURCE_REQUEST_STATUS_ACCEPTED){
            System.out.println(assetRequest.getStatus().getName());
            throw new ConflictException("La solicitud de activo con el id: " + assetRequestId + " ya fué aceptada.");
        }
        if(assetRequest.getStatus().getName() == EResourceRequestStatus.RESOURCE_REQUEST_STATUS_CANCELED){
            System.out.println(assetRequest.getStatus().getName());
            throw new ConflictException("La solicitud de activo con el id: " + assetRequestId + " ya fué cancelado.");
        }
        assetRequest.setStatus(
                resourceRequestStatusService.findByName(EResourceRequestStatus.RESOURCE_REQUEST_STATUS_CANCELED)
        );
        Asset asset = assetRequest.getAsset();
        asset.setStatus(
                assetStatusService.findByName(EAssetStatus.ASSET_STATUS_AVAILABLE)
        );
        AssetRequest saved = assetRequestRepository.save(assetRequest);

        ComposedMail composedMailToUser = MailFactory.createAssetRequestRejectEmail(assetRequest);
        mailService.sendComposedEmail(composedMailToUser);

        return assetRequestMapper.toDTO(saved);
    }

}
