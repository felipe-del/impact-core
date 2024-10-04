package com.impact.brain.asset.assetRequest.service.implement;

import com.impact.brain.asset.assetRequest.dto.AssetRequestDTO;
import com.impact.brain.asset.assetRequest.entity.AssetRequest;
import com.impact.brain.asset.assetRequest.repository.AssetRequestRepository;
import com.impact.brain.asset.assetRequest.repository.ResourceRequestStatusRepository;
import com.impact.brain.asset.assetRequest.service.IAssetRequestService;
import com.impact.brain.asset.entity.AssetStatus;
import com.impact.brain.asset.repository.AssetRepository;
import com.impact.brain.asset.repository.AssetStatusRepository;
import com.impact.brain.request.entity.Request;
import com.impact.brain.request.repository.RequestStatusRepository;
import com.impact.brain.request.service.implement.RequestService;
import com.impact.brain.user.service.impl.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author Isaac F. B. C.
 * @since 10/1/2024 - 7:31 PM
 */
@Service
public class AssetRequestService implements IAssetRequestService {
    private final RequestService requestService;
    private final AssetRequestRepository assetRequestRepository;
    private final AssetRepository assetRepository;
    private final AssetStatusRepository assetStatusRepository;
    private final UserService userService;
    private final ResourceRequestStatusRepository resourceRequestStatusRepository;
    private final RequestStatusRepository requestStatusRepository;
    public AssetRequestService(AssetRequestRepository assetRequestRepository,
                               RequestService requestService,
                               AssetRepository assetRepository,
                               AssetStatusRepository assetStatusRepository, UserService userService,
                               ResourceRequestStatusRepository resourceRequestStatusRepository, RequestStatusRepository requestStatusRepository){
        this.assetRequestRepository = assetRequestRepository;
        this.requestService = requestService;
        this.assetRepository = assetRepository;
        this.assetStatusRepository = assetStatusRepository;
        this.userService = userService;
        this.resourceRequestStatusRepository = resourceRequestStatusRepository;
        this.requestStatusRepository = requestStatusRepository;
    }

    @Override
    public Iterable<AssetRequest> findAll() {
        return assetRequestRepository.findAll();
    }

    @Override
    public AssetRequestDTO save(AssetRequestDTO assetRequestDTO) {
        Request r = new Request();
        r.setDate(LocalDate.now());
        r.setUser(userService.findById(1)); // AGARRARLO DE LA SESSION
        r.setStatus(requestStatusRepository.findById(1).get()); // ESTADO POR DEFAULT
        int requestID = requestService.save(r).getId();
        // Verificar que el ID de la Request guardada no sea null
        System.out.println("RequestID: "+requestID);
        AssetRequest ar = toEntity(assetRequestDTO);
        ar.setRequest(requestService.findById(requestID));
        return toDto(assetRequestRepository.save(ar));
    }

    private AssetRequestDTO toDto(AssetRequest assetRequest) {
        AssetRequestDTO assetRequestDTO = new AssetRequestDTO();
        assetRequestDTO.setId(assetRequest.getId());
        assetRequestDTO.setRequestId(assetRequest.getRequest().getId()); // Tomando el ID de la solicitud asociada
        assetRequestDTO.setAssetId(assetRequest.getAsset().getId()); // Tomando el ID del asset
        assetRequestDTO.setStatusId(assetRequest.getStatus().getId()); // Tomando el ID del status
        assetRequestDTO.setExpirationDate(assetRequest.getExpirationDate().toString()); // Convertir la fecha a String
        assetRequestDTO.setReason(assetRequest.getReason()); // Estableciendo la razón
        return assetRequestDTO;
    }

    private AssetRequest toEntity(AssetRequestDTO assetRequestDTO) {
        AssetRequest assetRequest = new AssetRequest();
        assetRequest.setId(0);
        assetRequest.setRequest(requestService.findById(1)); // esperando
        assetRequest.setAsset(assetRepository.findById(assetRequestDTO.getAssetId()).get());
        assetRequest.setStatus(resourceRequestStatusRepository.findById(assetRequestDTO.getStatusId()).get());
        assetRequest.setExpirationDate(LocalDate.parse(assetRequestDTO.getExpirationDate()));
        assetRequest.setReason(assetRequestDTO.getReason());
        return assetRequest;
    }
}
