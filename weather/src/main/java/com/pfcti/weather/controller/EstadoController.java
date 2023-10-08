package com.pfcti.weather.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sys")
@PreAuthorize("hasAuthority('SCOPE_READ')")
public class EstadoController {

    @GetMapping("/status")
    @Tag(name = "Verificar Estado", description = "Verifica el estado del api.")
    public Object estado() {
        return ResponseEntity.ok(
                "{\"message\": \"Todo correcto\"}"
        );
    }

}
