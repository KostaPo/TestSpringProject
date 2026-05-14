package com.example.testproject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * REST-контроллер для получения текущего времени.
 */
@RestController
public class TestController {

    private final TimeService timeService;

    /**
     * Конструктор контроллера.
     *
     * @param timeService сервис для получения времени
     */
    public TestController(TimeService timeService) {
        this.timeService = timeService;
    }

    /**
     * Возвращает карту с текущим временем.
     *
     * @return карта, содержащая текущее время
     */
    @GetMapping("/time")
    public Map<String, String> time() {
        return timeService.getCurrentTime();
    }
}
