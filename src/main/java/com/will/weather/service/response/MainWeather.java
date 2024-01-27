package com.will.weather.service.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class MainWeather {
    private BigDecimal temperature;
    private BigDecimal temperatureFeelsLike;
    private BigDecimal temperatureMin;
    private BigDecimal temperatureMax;
    private Integer pressure;
    private Integer humidity;

    @JsonProperty("main")
    private void unpackMain(Map<String, Object> main) {
        this.temperature = BigDecimal.valueOf((Double) main.get("temp"));
        this.temperatureFeelsLike = BigDecimal.valueOf((Double) main.get("feels_like"));
        this.temperatureMin = BigDecimal.valueOf((Double) main.get("temp_min"));
        this.temperatureMax = BigDecimal.valueOf((Double) main.get("temp_max"));
        this.humidity = (Integer) main.get("humidity");
        this.pressure = (Integer) main.get("pressure");
    }
}
