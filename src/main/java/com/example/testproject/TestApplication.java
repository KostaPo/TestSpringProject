package com.example.testproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Der Einstiegspunkt in die Spring Boot-Anwendung.
 */
@SpringBootApplication
public class TestApplication {
    /**
     * Die Haupteinstiegsmethode der Anwendung.
     *
     * @param args Befehlszeilenargumente
     */
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
