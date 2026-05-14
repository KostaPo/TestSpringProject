package com.example.testproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Einstiegspunkt der Spring Boot-Anwendung.
 */
@SpringBootApplication
public class TestApplication {
    /**
     * Methode zum Starten der Anwendung.
     *
     * @param args Befehlszeilenargumente
     */
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
