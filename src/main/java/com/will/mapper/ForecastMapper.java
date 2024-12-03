package com.will.mapper;

import com.will.dto.ForecastDto;
import com.will.entity.Coord;
import com.will.entity.Forecast;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Mapper(componentModel = "spring")
public interface ForecastMapper {

    @Mapping(source = "coordinate.lon", target = "lon")
    @Mapping(source = "coordinate.lat", target = "lat")
    @Mapping(source = "forecast.main.temp", target = "temp")
    @Mapping(source = "forecast.main.feelsLike", target = "feelsLike")
    @Mapping(source = "forecast.main.tempMin", target = "tempMin")
    @Mapping(source = "forecast.main.tempMax", target = "tempMax")
    @Mapping(source = "forecast.main.pressure", target = "pressure")
    @Mapping(source = "forecast.main.humidity", target = "humidity")
    @Mapping(source = "forecast.wind.speed", target = "speed")
    @Mapping(source = "forecast.wind.deg", target = "deg")
    @Mapping(source = "forecast.wind.gust", target = "gust")
    @Mapping(source = "forecast.dt", target = "currentTime", qualifiedByName = "convertToLocalDateTime")
    @Mapping(source = "forecast.sys.country", target = "country")
    @Mapping(source = "forecast.sys.sunrise", target = "sunriseTime", qualifiedByName = "convertToLocalDateTime")
    @Mapping(source = "forecast.sys.sunset", target = "sunsetTime", qualifiedByName = "convertToLocalDateTime")
    @Mapping(source = "forecast.name", target = "city")
    ForecastDto toDtoWithCoords(Forecast forecast, Coord coordinate);

    // TODO: need to teach receiving two values!!!
    @Named("convertToLocalDateTime")
    default LocalDateTime convertToLocalDateTime(Long timestampInMillis) {
        Instant instant = Instant.ofEpochSecond(timestampInMillis);
//        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(userOffsetInSeconds);
        return LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
    }
}
