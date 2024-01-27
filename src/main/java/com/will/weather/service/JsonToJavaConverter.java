package com.will.weather.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.will.weather.service.dto.LocationResponse;
import com.will.weather.service.dto.WeatherApiResponse;
import com.will.weather.service.response.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class JsonToJavaConverter {
    private final String jsonString;
    private final WeatherApiResponse weatherApiResponse;
    private final LocationResponse locationResponse;

    public JsonToJavaConverter(String jsonString) {
        this.jsonString = jsonString;
        weatherApiResponse = new WeatherApiResponse();
        this.locationResponse = new LocationResponse();
    }

    public LocationResponse convertToLocationResponse() throws JsonProcessingException {
        Coordinate lr = getValues(jsonString, Coordinate.class);
        Sys sys = getValues(jsonString, Sys.class);
        return mapToDTOObject(lr, sys);
    }

    public WeatherApiResponse convertToWeatherResponse() throws JsonProcessingException {
        Coordinate lr = getValues(jsonString, Coordinate.class);
        Wind wind = getValues(jsonString, Wind.class);
        MainWeather main = getValues(jsonString, MainWeather.class);
        Sys sys = getValues(jsonString, Sys.class);
        Weather weather = getValues(jsonString, Weather.class);
        return mapToDTOObject(lr, wind, main, sys, weather);
    }

    private <T> T getValues(String jsonString, Class<T> tClass) throws JsonProcessingException {
        return new ObjectMapper()
                .readerFor(tClass)
                .readValue(jsonString);
    }

    private LocationResponse mapToDTOObject(Coordinate lr, Sys sys) {
        locationResponse.setLatitude(lr.getLatitude());
        locationResponse.setLongitude(lr.getLongitude());
        locationResponse.setCityName(lr.getCityName());
        locationResponse.setCountryName(sys.getCountry());
        return locationResponse;
    }

    private WeatherApiResponse mapToDTOObject(Coordinate lr, Wind wind, MainWeather main, Sys sys, Weather weather) {
        weatherApiResponse.setCityName(lr.getCityName());
        weatherApiResponse.setLatitude(lr.getLatitude());
        weatherApiResponse.setLongitude(lr.getLongitude());
        weatherApiResponse.setSpeed(wind.getSpeed());
        weatherApiResponse.setWindTemp(wind.getWindTemp());
        weatherApiResponse.setGust(wind.getGust());
        weatherApiResponse.setTemperature(main.getTemperature().subtract(new BigDecimal("273.15")).setScale(2, RoundingMode.HALF_UP));
        weatherApiResponse.setTemperatureFeelsLike(main.getTemperatureFeelsLike().subtract(new BigDecimal("273.15")).setScale(2, RoundingMode.HALF_UP));
        weatherApiResponse.setTemperatureMin(main.getTemperatureMin().subtract(new BigDecimal("273.15")).setScale(2, RoundingMode.HALF_UP));
        weatherApiResponse.setTemperatureMax(main.getTemperatureMax().subtract(new BigDecimal("273.15")).setScale(2, RoundingMode.HALF_UP));
        weatherApiResponse.setPressure(main.getPressure());
        weatherApiResponse.setHumidity(main.getHumidity());
        weatherApiResponse.setCondition(weather.getCondition());
        weatherApiResponse.setCloudinessRate(weather.getCloudinessRate());
        weatherApiResponse.setSunriseTime(TimeConverter.convertToLocalDateTime(sys.getSunriseTime()));
        weatherApiResponse.setSunsetTime(TimeConverter.convertToLocalDateTime(sys.getSunsetTime()));
        return weatherApiResponse;
    }
}
