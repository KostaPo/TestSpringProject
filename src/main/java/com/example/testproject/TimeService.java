package com.example.testproject;

import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Map;

/**
 * Service for getting the current time.
 */
@Service
public class TimeService {

    /**
     * Returns a map with the current time.
     *
     * @return a map containing a string representation of the current time
     */
    public Map<String, String> getCurrentTime() {
        return Map.of("time", OffsetDateTime.now(ZoneId.of("Europe/Moscow")).toString());
    }
}
