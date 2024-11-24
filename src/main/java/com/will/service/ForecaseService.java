package com.will.service;

import com.will.dto.LocationDto;
import com.will.parse.CoordinateParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ForecaseService {

    private final OpenWeatherApiClient client;
    private final CoordinateParser coordinateParser;

    public List<LocationDto> getCoordinatesByCity(String city) throws URISyntaxException, IOException, InterruptedException {
        HttpResponse<String> response = client.getCoordinatesByCity(city);
        if (response.statusCode() != 200) {
            // TODO: here I need to throw exception some kinda
            return null;
        }
        return coordinateParser.parse(response.body());
    }
}
