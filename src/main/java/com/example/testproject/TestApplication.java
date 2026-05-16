package com.example.testproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Точка входа в приложение Spring Boot.
 */
@SpringBootApplication
public class TestApplication {
    /**
     * Основной метод входа в приложение.
     *
     * @param args Аргументы командной строки
     */
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
