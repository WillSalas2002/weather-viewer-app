package com.will.parse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.will.dto.LocationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoordinateParser implements JsonParser<List<LocationDto>> {

    private final ObjectMapper objectMapper;

    @Override
    public List<LocationDto> parse(String json) throws JsonProcessingException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        CollectionType javaType = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, LocationDto.class);
        return objectMapper.readValue(json, javaType);
    }
}
