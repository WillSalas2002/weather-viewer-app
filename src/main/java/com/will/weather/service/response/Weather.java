package com.will.weather.service.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class Weather {
    private String condition;
    private String cloudinessRate;

    @JsonProperty("weather")
    private void unpackWeather(List<Map<String, Object>> weatherList) {

        if (weatherList != null) {
            Map<String, Object> first = weatherList.get(0);
            this.condition = (String) first.get("main");
            this.cloudinessRate = (String) first.get("icon");
        }
    }
}
