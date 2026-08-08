package com.example.testproject.service;

import com.example.testproject.entity.RequestLog;
import com.example.testproject.repository.RequestLogRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Map;

@Service
public class TimeService {

    private final RequestLogRepository logRepository;

    public TimeService(RequestLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public Map<String, String> getCurrentTime() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        OffsetDateTime now = OffsetDateTime.now(ZoneId.of("Europe/Moscow"));

        // Логируем запрос
        RequestLog log = new RequestLog(userId, now, "/time");
        logRepository.save(log);

        return Map.of("time", now.toString());
    }
}