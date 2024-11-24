package com.will.mapper;

import com.will.dto.ForecastDto;
import com.will.entity.Forecast;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ForecastMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "login", target = "login")
    @Mapping(source = "password", target = "password")
    ForecastDto toDto(Forecast forecast);
}
