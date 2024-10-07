package com.impact.brain.request.service.implement;

import com.impact.brain.asset.assetRequest.repository.AssetRequestRepository;
import com.impact.brain.request.entity.Request;
import com.impact.brain.request.repository.RequestRepository;
import com.impact.brain.request.service.IRequestService;
import org.springframework.stereotype.Service;

/**
 * @author Isaac F. B. C.
 * @since 10/3/2024 - 10:41 AM
 */
@Service
public class RequestService implements IRequestService {
    private final RequestRepository requestRepository;
    public RequestService(RequestRepository requestRepository){
        this.requestRepository = requestRepository;
    }

    @Override
    public Iterable<Request> findAll() {
        return requestRepository.findAll();
    }

    @Override
    public Request save(Request request) {
        return requestRepository.save(request);
    }

    @Override
    public Request findById(int id) {
        return requestRepository.findById(id).orElse(null);
    }

}
