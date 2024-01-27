package com.will.weather.service.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WeatherApiResponse {

    private BigDecimal latitude;
    private BigDecimal longitude;
    private BigDecimal temperature;
    private BigDecimal temperatureFeelsLike;
    private BigDecimal temperatureMin;
    private BigDecimal temperatureMax;
    private Integer pressure;
    private Integer humidity;
    private Double speed;
    private Double windTemp;
    private Double gust;
    private String condition;
    private String cloudinessRate;
    private LocalDateTime sunriseTime;
    private LocalDateTime sunsetTime;
    private String cityName;
}
