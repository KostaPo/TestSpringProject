package com.example.testproject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * Controller REST per ottenere l'ora corrente.
 */
@RestController
public class TestController {

    private final TimeService timeService;

    /**
     * Costruttore del controller.
     *
     * @param timeService servizio per ottenere l'ora
     */
    public TestController(TimeService timeService) {
        this.timeService = timeService;
    }

    /**
     * Restituisce una mappa con l'ora.
     *
     * @return mappa contenente l'ora corrente
     */
    @GetMapping("/time")
    public Map<String, String> time() {
        return timeService.getCurrentTime();
    }
}
