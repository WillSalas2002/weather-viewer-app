package com.will.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
public class OpenWeatherApiClient {

    private String GEO_API_URL_TEMPLATE = "https://api.openweathermap.org/geo/1.0/direct?q=%s&limit=5&appid=%s";
    private String GEOLOCATION_API_URL_TEMPLATE = "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s";
    private final String API_KEY = "7c4b89b1b811d4a287d45d0fab5564ec";
    private final HttpClient client = HttpClient.newHttpClient();

    public HttpResponse<String> getCoordinatesByCity(String city) throws URISyntaxException, IOException, InterruptedException {
        String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
        String url = String.format(GEO_API_URL_TEMPLATE, encodedCity, API_KEY);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> getForecastByCoordinates(String lat, String lon) throws URISyntaxException, IOException, InterruptedException {
        String url = String.format(GEOLOCATION_API_URL_TEMPLATE, lat, lon, API_KEY);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
