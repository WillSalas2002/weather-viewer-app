package com.will.weather.service.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class LocationResponse {
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String cityName;
    private String countryName;
}