package com.will.service;

import com.will.dto.LocationDto;
import com.will.dto.UserDto;
import com.will.entity.Coord;
import com.will.mapper.LocationMapper;
import com.will.model.Location;
import com.will.model.User;
import com.will.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final UserService userService;
    private final LocationMapper locationMapper;
    private final LocationRepository locationRepository;

    public void save(LocationDto locationDto, String sessionId) {
        Location location = locationMapper.toModel(locationDto);
        UserDto userDto = userService.findUserBySessionId(sessionId);
        User user = new User(userDto.getId());
        locationRepository.save(location, user);
    }

    public List<Coord> findSavedCoordinates(UserDto userDto) {
        List<Location> locations = locationRepository.findAllByUserId(userDto.getId());
        return locations.stream()
                .map(locationMapper::toCoord)
                .toList();
    }

    public void remove(String lon, String lat, String sessionId) {
        BigDecimal longitude = new BigDecimal(lon);
        BigDecimal latitude = new BigDecimal(lat);
        UserDto userDto = userService.findUserBySessionId(sessionId);
        locationRepository.removeFromUserLocation(longitude, latitude, new User(userDto.getId()));
    }
}
