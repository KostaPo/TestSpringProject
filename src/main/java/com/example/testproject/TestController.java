package com.example.testproject;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/time")
    public Map<String, String> time() {
        return Map.of("time", OffsetDateTime.now(ZoneOffset.UTC).toString());
    }
}
