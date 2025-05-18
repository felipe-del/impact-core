package com.impact.core.module.request.service;

import com.impact.core.module.assetRequest.entity.AssetRequest;
import com.impact.core.module.assetRequest.service.AssetRequestService;
import com.impact.core.module.productRequest.entity.ProductRequest;
import com.impact.core.module.productRequest.service.ProductRequestService;
import com.impact.core.module.request.mapper.RequestMapper;
import com.impact.core.module.request.payload.RequestDTOResponse;
import com.impact.core.module.spaceRequest_Reservation.entity.SpaceRequest;
import com.impact.core.module.spaceRequest_Reservation.service.SpaceRequestAndReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsible for handling requests of different types: Asset, Product, and Space.
 * <p>
 * This service aggregates the functionality from multiple request services, including asset,
 * product, and space requests. It provides the necessary business logic to fetch and map
 * pending requests from each type into a unified response format.
 */
@Service
@RequiredArgsConstructor
public class RequestService {

    private final AssetRequestService assetRequestService;
    private final ProductRequestService productRequestService;
    private final SpaceRequestAndReservationService spaceRequestService;
    private final RequestMapper requestMapper;

    /**
     * Retrieves all pending requests across different types: Asset, Product, and Space.
     * <p>
     * This method fetches all pending requests for each request type (asset, product, and space)
     * and maps them into a unified list of response objects that can be sent to the frontend.
     *
     * @return A list of {@link RequestDTOResponse} objects representing the pending requests.
     */
    public List<RequestDTOResponse> getAllPendingRequests() {
        List<AssetRequest> assetRequests = assetRequestService.findByPending();
        List<ProductRequest> productRequests = productRequestService.findByPending();
        List<SpaceRequest> spaceRequests = spaceRequestService.findByPending();

        return requestMapper.toDTOList(assetRequests, productRequests, spaceRequests);
    }
}
