package com.pfcti.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
public class ClimaRes {
    @Getter
    @Setter
    private String weather;
    @Getter
    @Setter
    private float tempMin;
    @Getter
    @Setter
    private float tempMax;
    @Getter
    @Setter
    private float humidity;
}
