package com.example.testproject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * REST controller for retrieving the current time.
 */
@RestController
public class TestController {

    private final TimeService timeService;

    /**
     * Controller constructor.
     *
     * @param timeService Time service for retrieving the current time
     */
    public TestController(TimeService timeService) {
        this.timeService = timeService;
    }

    /**
     * Returns a map with the current time.
     *
     * @return Map containing the current time
     */
    @GetMapping("/time")
    public Map<String, String> time() {
        return timeService.getCurrentTime();
    }
}
