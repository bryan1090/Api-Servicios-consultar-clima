package com.pfcti.weather.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
    //En esta clase se manejan los tipos de excepciones que arroje la aplicación
    //Los errores no definidos explicitamente caerán en el método que maneja la clase general Exception

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE) //devuelve un código 503
    public ResponseEntity<ExceptionResponse> manejarError_noDefinidos(Exception e) {
        logger.error("Se produjo un error", e);
        String [] errors={e.getMessage()};
        return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(HttpStatus.SERVICE_UNAVAILABLE.value(),
                errors),
                HttpStatus.SERVICE_UNAVAILABLE);


    }
    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)//devuelve un código 400
    public ResponseEntity<ExceptionResponse> manejarError_formatoIncorrecto(HttpClientErrorException.BadRequest e) {
        logger.error("Error de petición incorrecta",e);
        String [] errors={e.getMessage()};
        return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(HttpStatus.BAD_REQUEST.value(),
                errors),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)//devuelve un código 401
    public ResponseEntity<ExceptionResponse> manejarError_noAutorizado(HttpClientErrorException.Unauthorized e) {
        logger.error("Error de falta de autorización", e);
        String [] errors={e.getMessage()};
        return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(HttpStatus.UNAUTHORIZED.value(),
                errors),
                HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)//devuelve un código 404
    public ResponseEntity<ExceptionResponse> manejarError_noEncontrado(HttpClientErrorException.Unauthorized e) {
        logger.error("Error de no encontrado", e);
        String [] errors={e.getMessage()};
        return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(HttpStatus.NOT_FOUND.value(),
                errors),
                HttpStatus.NOT_FOUND);
    }

}
