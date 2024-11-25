package com.will.service;

import com.will.dto.LocationDto;
import com.will.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public void save(LocationDto locationDto, String sessionId) {
        System.out.println(locationDto);
        System.out.println(sessionId);
    }
}
