package com.pfcti.weather.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
public class ExceptionResponse {
    @Getter
    @Setter
    private int code;
    @Getter
    @Setter
    private String[] errors;
}
