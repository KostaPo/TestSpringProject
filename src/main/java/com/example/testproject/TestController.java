package com.example.testproject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * Controlador REST para obtener la hora actual.
 */
@RestController
public class TestController {

    private final TimeService timeService;

    /**
     * Constructor del controlador.
     *
     * @param timeService Servicio de tiempo para obtener la hora
     */
    public TestController(TimeService timeService) {
        this.timeService = timeService;
    }

    /**
     * Devuelve un mapa con la hora actual.
     *
     * @return Mapa que contiene la hora actual
     */
    @GetMapping("/time")
    public Map<String, String> time() {
        return timeService.getCurrentTime();
    }
}
