package com.example.testproject;

import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Map;

/**
 * Dienst zum Abrufen der aktuellen Zeit.
 */
@Service
public class TimeService {

    /**
     * Gibt eine Karte mit der aktuellen Zeit zurück.
     *
     * @return Karte, die die textuelle Darstellung der aktuellen Zeit enthält
     */
    public Map<String, String> getCurrentTime() {
        return Map.of("time", OffsetDateTime.now(ZoneId.of("Europe/Moscow")).toString());
    }
}
