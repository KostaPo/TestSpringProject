package com.example.testproject;

import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Map;

/**
 * Servicio para obtener la hora actual.
 */
@Service
public class TimeService {

    /**
     * Devuelve un mapa con la hora actual.
     *
     * @return un mapa que contiene una representación en cadena de la hora actual
     */
    public Map<String, String> getCurrentTime() {
        return Map.of("time", OffsetDateTime.now(ZoneId.of("Europe/Moscow")).toString());
    }
}
