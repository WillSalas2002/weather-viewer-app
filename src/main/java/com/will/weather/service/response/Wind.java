package com.will.weather.service.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Wind {
    private Double speed;
    private Double windTemp;
    private Double gust;

    @JsonProperty("wind")
    private void unpackWind(Map<String, Double> wind) {
        this.speed = wind.get("speed");
        this.windTemp = wind.get("deg");
        this.gust = wind.get("gust");
    }
}
