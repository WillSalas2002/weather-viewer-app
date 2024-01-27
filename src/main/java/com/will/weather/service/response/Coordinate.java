package com.will.weather.service.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Coordinate {

    private BigDecimal latitude;
    private BigDecimal longitude;
    @JsonProperty("name")
    private String cityName;

    @JsonProperty("coord")
    private void unpackCoord(Map<String, BigDecimal> coord) {
        this.latitude = coord.get("lat");
        this.longitude = coord.get("lon");
    }
}
