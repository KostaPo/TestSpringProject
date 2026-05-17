package com.example.testproject;

import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Map;

/**
 * Service for getting the current time.
 * @author AI Agent
 * @deprecated false
 * @see java.time.LocalDateTime
 */
@Service
public class TimeService {

    /**
     * Returns a Map with the current time.
     *
     * @return Map containing a textual representation of the current time
     */
    public Map<String, String> getCurrentTime() {
        return Map.of("time", OffsetDateTime.now(ZoneId.of("Europe/Moscow")).toString(), "error", "ERROR");
    }
}
