package com.will.weather.service;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WeatherClient {
    private static final String WEATHER_API_KEY = "7c4b89b1b811d4a287d45d0fab5564ec";
    private static final String WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/weather";

    public String getWeatherByCityName(String cityName) throws IOException {
        String encodedCityName = URLEncoder.encode(cityName, StandardCharsets.UTF_8);
        String url = WEATHER_API_URL + "?q=" + encodedCityName + "&appid=" + WEATHER_API_KEY;
        return getResponseAsString(url);
    }

    public String getWeatherByCoordinates(BigDecimal latitude, BigDecimal longitude) throws IOException {
        String url = WEATHER_API_URL + "?lat=" + latitude + "&lon=" + longitude + "&appid=" + WEATHER_API_KEY;
        return getResponseAsString(url);
    }

    private String getResponseAsString(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return convertJsonToString(connection.getInputStream());
        } else {
            throw new RuntimeException("Could not get response from Weather Api");
        }
    }

    private String convertJsonToString(InputStream inputStream) throws IOException {
        BufferedReader bis = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bis.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}
