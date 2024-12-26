package com.impact.core.module.locationNumber.service;

import com.impact.core.module.locationNumber.mapper.LocationNumberMapper;
import com.impact.core.module.locationNumber.repository.LocationNumberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("locationNumberService")
@RequiredArgsConstructor
public class LocationNumberService {
    public final LocationNumberRepository locationNumberRepository;
    public final LocationNumberMapper locationNumberMapper;


}
