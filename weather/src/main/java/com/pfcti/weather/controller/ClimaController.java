package com.pfcti.weather.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfcti.weather.model.Clima;
import com.pfcti.weather.model.ClimaReq;
import com.pfcti.weather.model.ClimaRes;
import com.pfcti.weather.repository.ClimaRepo;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/")
@Configuration
public class ClimaController {
    private static final Logger logger = LoggerFactory.getLogger(ClimaController.class);

    @Getter
    @Value("${appid}")
    private String appid;

    @Getter
    @Value("${url}")
    private String url;
    @Autowired
    private ClimaRepo climaRepo;

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ClimaRes.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "No existió una autorización.", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "No se pudo completar la consulta.", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "503", description = "Ocurrió un error no controlado.", content = {@Content(schema = @Schema())})
    })
    @PostMapping("/weather")
    @Tag(name = "Obtener Clima", description = "Obtiene el clima a travéz de un servicio público.")
    @PreAuthorize("hasAuthority('SCOPE_READ')")
    @CrossOrigin(origins = "*")
    public Object obtenerClima(@RequestBody ClimaReq req) {
        //Validar si ya se tengo la información
        Clima cl_found=climaRepo.findFirstByLatAndLonAndCreatedBetween(req.getLat(),req.getLon(),LocalDateTime.now().minusMinutes(10),LocalDateTime.now());

        if(cl_found!=null &&cl_found.getId()!=null)
        {
            //Devuelvo la información previamente guardada en memoria.
            ClimaRes resp = new ClimaRes();
            resp=new ClimaRes(cl_found.getWeather()
                    ,Float.valueOf(cl_found.getTempMin())
                    ,Float.valueOf(cl_found.getTempMax())
                    ,Float.valueOf(cl_found.getHumidity())
            );

            return resp;
        }

        // Crear un objeto RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Crear los parámetros de la solicitud
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("lat", String.valueOf(req.getLat()));
        parameters.add("lon", String.valueOf(req.getLon()));
        parameters.add("appid", appid);

        // Crear la URL de la solicitud
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        builder.queryParams(parameters);
        String url = builder.build().toUriString();

        // Realizar la solicitud HTTP
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, null, String.class);

        // Inicializar un objeto ObjectMapper de Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        ClimaRes resp = new ClimaRes();

        try {
            logger.info("Parseando la cadena Json");

            // Parsear la cadena JSON en un nodo JsonNode
            JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());

            // Acceder a los datos uno por uno
             resp=new ClimaRes(jsonNode.get("weather").get(0).get("main").asText()
                    ,Float.valueOf(jsonNode.get("main").get("temp_min").asText())
                    ,Float.valueOf(jsonNode.get("main").get("temp_max").asText())
                    ,Float.valueOf(jsonNode.get("main").get("humidity").asText())
                    );


        } catch (Exception e) {
            logger.error("Error al parsear la cadena Json",e);
            e.printStackTrace();
        }

        if(resp.getWeather()!=null&&resp.getWeather()!="")
        {
            Clima c =new Clima(req.getLat(),req.getLon(),resp.getWeather()
                    ,resp.getTempMin()
                    ,resp.getTempMax()
                    ,resp.getHumidity()
                    ,LocalDateTime.now()
            );
            climaRepo.save(c);
        }

        //Devuelvo la información consultada con el servicio
        return resp;
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ClimaRes.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "401", description = "No existió una autorización.", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "No se pudo completar la consulta.", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "503", description = "Ocurrió un error no controlado.", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/history")
    @Tag(name = "Obtener Lista de Clima", description = "Obtiene una lista de todos los valores de clima, que fueron guardados previamente al consultar.")
    @PreAuthorize("hasAuthority('SCOPE_READ')")
    public Object obtenerListaClima() {
        return climaRepo.findAll();

    }
}