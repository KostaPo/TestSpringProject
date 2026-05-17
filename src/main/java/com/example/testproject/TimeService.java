package com.example.testproject;

import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Map;

/**
 * Сервис для получения текущего времени.
 * @author AI Agent
 */
@Service
public class TimeService {

    /**
     * Возвращает Map с текущим временем.
     *
     * @return Map, содержащий текстовое представление текущего времени
     */
    public Map<String, String> getCurrentTime() {
        return Map.of("time", OffsetDateTime.now(ZoneId.of("Europe/Moscow")).toString(), "error", "ERROR");
    }
}
