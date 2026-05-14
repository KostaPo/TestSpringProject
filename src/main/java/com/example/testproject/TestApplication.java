package com.example.testproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto di ingresso dell'applicazione Spring Boot.
 */
@SpringBootApplication
public class TestApplication {
    /**
     * Metodo di avvio dell'applicazione.
     *
     * @param args argomenti della riga di comando
     */
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
