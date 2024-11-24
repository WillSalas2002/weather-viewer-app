package com.will.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Forecast {

    private Coord coord;
    private List<Weather> weather;
    private Main main;
    private Wind wind;
    private Long dt;
    private Sys sys;
    private Integer timezone;
    private String name;
}
