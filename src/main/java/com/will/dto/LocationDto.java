package com.will.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {

    private String name;
    private String state;
    private String country;
    private BigDecimal lon;
    private BigDecimal lat;
}
