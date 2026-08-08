package com.example.testproject.controller;

import com.example.testproject.service.TimeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class TestController {

    private final TimeService timeService;

    public TestController(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping("/time")
    public Map<String, String> time() {
        return timeService.getCurrentTime();
    }
}
