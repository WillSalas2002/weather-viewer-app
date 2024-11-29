package com.will.mapper;

import com.will.dto.ForecastDto;
import com.will.entity.Forecast;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Mapper(componentModel = "spring")
public interface ForecastMapper {

    @Mapping(source = "coord.lon", target = "lon")
    @Mapping(source = "coord.lat", target = "lat")
    @Mapping(source = "main.temp", target = "temp")
    @Mapping(source = "main.feelsLike", target = "feelsLike")
    @Mapping(source = "main.tempMin", target = "tempMin")
    @Mapping(source = "main.tempMax", target = "tempMax")
    @Mapping(source = "main.pressure", target = "pressure")
    @Mapping(source = "main.humidity", target = "humidity")
    @Mapping(source = "wind.speed", target = "speed")
    @Mapping(source = "wind.deg", target = "deg")
    @Mapping(source = "wind.gust", target = "gust")
    @Mapping(source = "dt", target = "currentTime", qualifiedByName = "convertToLocalDateTime")
    @Mapping(source = "sys.country", target = "country")
    @Mapping(source = "sys.sunrise", target = "sunriseTime", qualifiedByName = "convertToLocalDateTime")
    @Mapping(source = "sys.sunset", target = "sunsetTime", qualifiedByName = "convertToLocalDateTime")
    @Mapping(source = "name", target = "city")
    ForecastDto toDto(Forecast forecast);

    // TODO: need to teach receiving two values!!!
    @Named("convertToLocalDateTime")
    default LocalDateTime convertToLocalDateTime(Long timestampInMillis) {
        Instant instant = Instant.ofEpochSecond(timestampInMillis);
//        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(userOffsetInSeconds);
        return LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
    }
}
