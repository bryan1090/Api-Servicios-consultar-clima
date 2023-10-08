package com.pfcti.weather.repository;

import com.pfcti.weather.model.Clima;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ClimaRepo extends CrudRepository<Clima, Long> {

    Clima findFirstByLatAndLonAndCreatedBetween(Double lat, Double lon, LocalDateTime fecha,LocalDateTime fecha2);


}
