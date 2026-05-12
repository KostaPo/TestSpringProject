package com.example.testproject;

import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Map;

@Service
public class TimeService {

    public Map<String, String> getCurrentTime() {
        return Map.of("time", OffsetDateTime.now(ZoneId.of("Europe/Moscow")).toString());
    }
}