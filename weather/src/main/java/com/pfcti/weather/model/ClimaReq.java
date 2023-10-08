package com.pfcti.weather.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
public class ClimaReq {

        @Getter
        @Setter
        private double lat;
        @Getter
        @Setter
        private double lon;


    }
