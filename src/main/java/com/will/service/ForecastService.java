package com.will.service;

import com.will.dto.ForecastDto;
import com.will.dto.LocationDto;
import com.will.entity.Coord;
import com.will.entity.Forecast;
import com.will.mapper.ForecastMapper;
import com.will.parse.CoordinateParser;
import com.will.parse.ForecastParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ForecastService {

    private final OpenWeatherApiClient client;
    private final ForecastParser forecastParser;
    private final ForecastMapper forecastMapper;
    private final CoordinateParser coordinateParser;

    public List<LocationDto> getCoordinatesByCity(String city) throws URISyntaxException, IOException, InterruptedException {
        HttpResponse<String> response = client.getCoordinatesByCity(city);
        if (response.statusCode() != 200) {
            // TODO: here I need to throw exception some kinda
            return null;
        }
        return coordinateParser.parse(response.body());
    }

    public ForecastDto getForecastByCoordinates(Coord coordinates) throws URISyntaxException, IOException, InterruptedException {
        HttpResponse<String> response = client.getForecastByCoordinates(coordinates.getLat(), coordinates.getLon());
        if (response.statusCode() != 200) {
            // TODO: here I need to throw exception some kinda
            return null;
        }
        Forecast forecast = forecastParser.parse(response.body());
        // TODO: finish parsing here
        return forecastMapper.toDto(forecast);
    }
}
