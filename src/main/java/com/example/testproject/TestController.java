package com.example.testproject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * REST controller for getting the current time.
 */
@RestController
public class TestController {

    private final TimeService timeService;

    /**
     * Controller constructor.
     *
     * @param timeService service for getting the time
     */
    public TestController(TimeService timeService) {
        this.timeService = timeService;
    }

    /**
     * Returns a map with the time.
     *
     * @return a map containing the current time
     */
    @GetMapping("/time")
    public Map<String, String> time() {
        return timeService.getCurrentTime();
    }
}
