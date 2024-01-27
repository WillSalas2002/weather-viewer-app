package com.will.weather.service.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Sys {
    private Integer sunriseTime;
    private Integer sunsetTime;
    private String country;

    @JsonProperty("sys")
    private void unpackSys(Map<String, Object> sys) {
        this.sunriseTime = (Integer) sys.get("sunrise");
        this.sunsetTime = (Integer) sys.get("sunset");
        this.country = (String) sys.get("country");
    }
}
