package com.example.testproject;

import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Map;

/**
 * Servizio per ottenere l'ora corrente.
 */
@Service
public class TimeService {

    /**
     * Restituisce una mappa con l'ora corrente.
     *
     * @return mappa contenente la rappresentazione testuale dell'ora corrente
     */
    public Map<String, String> getCurrentTime() {
        return Map.of("time", OffsetDateTime.now(ZoneId.of("Europe/Moscow")).toString());
    }
}
