package com.will.weather.service.response;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeConverter {

    public static LocalDateTime convertToLocalDateTime(Object value) {
        Instant instant = Instant.ofEpochSecond(((Integer) value));
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }
}
