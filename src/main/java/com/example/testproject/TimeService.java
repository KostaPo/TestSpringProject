package com.example.testproject;

import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Map;

/**
 * Сервис для получения текущего времени.
 */
@Service
public class TimeService {

    /**
     * Возвращает карту с текущим временем.
     *
     * @return карта, содержащая строковое представление текущего времени
     */
    public Map<String, String> getCurrentTime() {
        return Map.of("time", OffsetDateTime.now(ZoneId.of("Europe/Moscow")).toString());
    }
}
