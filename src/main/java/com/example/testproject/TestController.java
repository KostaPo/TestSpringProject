package com.example.testproject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * REST-контроллер для получения текущего времени.
 *
 * @since 1.0
 */
@RestController
public class TestController {

    private final TimeService timeService;

    /**
     * Конструктор контроллера.
     *
     * @param timeService Сервис для получения текущего времени
     */
    public TestController(TimeService timeService) {
        this.timeService = timeService;
    }

    /**
     * Возвращает Map с текущим временем.
     *
     * @return Map, содержащий ключ "time" со строковым представлением текущего времени.
     */
    @GetMapping("/time")
    public Map<String, String> time() {
        return timeService.getCurrentTime();
    }
}
