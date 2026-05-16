package com.example.testproject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * REST-Controller zum Abrufen der aktuellen Zeit.
 */
@RestController
public class TestController {

    private final TimeService timeService;

    /**
     * Konstruktor des Controllers.
     *
     * @param timeService Time-Service zum Abrufen der aktuellen Zeit
     */
    public TestController(TimeService timeService) {
        this.timeService = timeService;
    }

    /**
     * Gibt eine Map mit der aktuellen Zeit zurück.
     *
     * @return Map, die die aktuelle Zeit enthält
     */
    @GetMapping("/time")
    public Map<String, String> time() {
        return timeService.getCurrentTime();
    }
}
