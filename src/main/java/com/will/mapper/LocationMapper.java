package com.will.mapper;

import com.will.dto.LocationDto;
import com.will.entity.Coord;
import com.will.model.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    @Mapping(source = "name", target = "title")
    @Mapping(source = "lon", target = "longitude", qualifiedByName = "remakeCoordinates")
    @Mapping(source = "lat", target = "latitude", qualifiedByName = "remakeCoordinates")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    Location toModel(LocationDto locationDto);

    @Mapping(source = "longitude", target = "lon")
    @Mapping(source = "latitude", target = "lat")
    Coord toCoord(Location location);

    @Named("remakeCoordinates")
    default BigDecimal remakeCoordinates(BigDecimal coord) {
        return coord.setScale(5, RoundingMode.DOWN);
    }

}
