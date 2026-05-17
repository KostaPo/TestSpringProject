package com.example.testproject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * REST controller for retrieving the current time.
 *
 * @since 1.0
 */
@RestController
public class TestController {

    private final TimeService timeService;

    /**
     * Конструктор контроллера.
     *
     * @param timeService Time service for retrieving the current time
     */
    public TestController(TimeService timeService) {
        this.timeService = timeService;
    }

    /**
     * Возвращает Map с текущим временем.
     *
     * @return Map containing the key "time" with the current time as a string.
     */
    @GetMapping("/time")
    public Map<String, String> time() {
        return timeService.getCurrentTime();
    }
}
