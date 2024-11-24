package com.will.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ForecastDto {

    private Double temp;
    private Double feelsLike;
    private Double tempMin;
    private Double tempMax;
    private Integer pressure;
    private Integer humidity;
    private Double speed;
    private Double deg;
    private Double gust;
    private LocalDateTime currentTime;
    private String country;
    private LocalDateTime sunriseTime;
    private LocalDateTime sunsetTime;
    private String city;
}
