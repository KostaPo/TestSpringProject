package com.example.testproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point of the Spring Boot application.
 */
@SpringBootApplication
public class TestApplication {
    /**
     * Application startup method.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
