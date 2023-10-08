package com.pfcti.weather.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Clima {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private double lat;
    @Getter
    @Setter
    private double lon;
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
    @Getter
    @Setter
    private LocalDateTime created;

    public Clima(double lat, double lon, String weather, float tempMin, float tempMax, float humidity, LocalDateTime created) {
        this.lat = lat;
        this.lon = lon;
        this.weather = weather;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.humidity = humidity;
        this.created = created;
    }
}
