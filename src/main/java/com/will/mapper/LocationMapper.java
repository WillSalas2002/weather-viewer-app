package com.will.mapper;

import com.will.dto.LocationDto;
import com.will.entity.Coord;
import com.will.model.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    @Mapping(source = "name", target = "title")
    @Mapping(source = "lon", target = "longitude")
    @Mapping(source = "lat", target = "latitude")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    Location toModel(LocationDto locationDto);

    @Mapping(source = "longitude", target = "lon")
    @Mapping(source = "latitude", target = "lat")
    Coord toCoord(Location location);

}
