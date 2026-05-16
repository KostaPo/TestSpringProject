package com.example.testproject;

import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Map;

/**
 * Service zum Abrufen der aktuellen Zeit.
 */
@Service
public class TimeService {

    /**
     * Gibt eine Map mit der aktuellen Zeit zurück.
     *
     * @return Map, die eine textuelle Darstellung der aktuellen Zeit enthält
     */
    public Map<String, String> getCurrentTime() {
        return Map.of("time", OffsetDateTime.now(ZoneId.of("Europe/Moscow")).toString());
    }
}
