package com.impact.core.module.request.service;

import com.impact.core.module.assetRequest.entity.AssetRequest;
import com.impact.core.module.assetRequest.repository.AssetRequestRepository;
import com.impact.core.module.assetRequest.service.AssetRequestService;
import com.impact.core.module.productRequest.entity.ProductRequest;
import com.impact.core.module.productRequest.repository.ProductRequestRepository;
import com.impact.core.module.productRequest.service.ProductRequestService;
import com.impact.core.module.request.mapper.RequestMapper;
import com.impact.core.module.request.payload.RequestDTOResponse;
import com.impact.core.module.spaceRequest_Reservation.entity.SpaceRequest;
import com.impact.core.module.spaceRequest_Reservation.repository.SpaceRequestRepository;
import com.impact.core.module.spaceRequest_Reservation.service.SpaceRndRService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final AssetRequestService assetRequestService;
    private final ProductRequestService productRequestService;
    private final SpaceRndRService spaceRequestService;
    private final RequestMapper requestMapper;

    public List<RequestDTOResponse> getAllPendingRequests() {
        List<AssetRequest> assetRequests = assetRequestService.findByPending();
        List<ProductRequest> productRequests = productRequestService.findByPending();
        List<SpaceRequest> spaceRequests = spaceRequestService.findByPending();

        return requestMapper.toDTOList(assetRequests, productRequests, spaceRequests);
    }
}
