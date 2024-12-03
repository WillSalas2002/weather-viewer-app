package com.will.service;

import com.will.dto.ForecastDto;
import com.will.dto.LocationDto;
import com.will.entity.Coord;
import com.will.mapper.ForecastMapper;
import com.will.parse.CoordinateParser;
import com.will.parse.ForecastParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    public List<ForecastDto> getForecastByCoordinates(List<Coord> coordinates) throws URISyntaxException, IOException, InterruptedException {
        return coordinates.stream()
                .map(coord -> Map.entry(coord, getCoordHttpResponse(coord))) // Pair Coord with HttpResponse
                .map(entry -> Map.entry(entry.getKey(), entry.getValue().body())) // Extract body and keep Coord
                .map(entry -> parseAndMapToDto(entry.getValue(), entry.getKey())) // Pass body and Coord
                .filter(Objects::nonNull)
                .toList();
    }

    private HttpResponse<String> getCoordHttpResponse(Coord coord) {
        try {
            return client.getForecastByCoordinates(coord.getLat(), coord.getLon());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private ForecastDto parseAndMapToDto(String body, Coord coord) {
        try {
            return forecastMapper.toDtoWithCoords(forecastParser.parse(body), coord);
        } catch (Exception e) {
            System.out.printf("Failed to process forecast: {%s}", body);
            throw new RuntimeException(e);
        }
    }
}
