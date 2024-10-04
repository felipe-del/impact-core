package com.impact.brain.request.service;

import com.impact.brain.request.entity.Request;

/**
 * @author Isaac F. B. C.
 * @since 10/3/2024 - 10:41 AM
 */
public interface IRequestService {
    Iterable<Request> findAll();
    Request save(Request request);
    Request findById(int id);
}
