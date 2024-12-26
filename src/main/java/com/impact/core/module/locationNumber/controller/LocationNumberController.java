package com.impact.core.module.locationNumber.controller;

import com.impact.core.module.locationNumber.service.LocationNumberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/location-number")
@RequiredArgsConstructor
public class LocationNumberController {
    public final LocationNumberService locationNumberService;
}
