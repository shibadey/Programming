package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main entry point for the Spring Boot Application.
 * This application demonstrates best practices including:
 * - Logging
 * - Exception Handling
 * - RESTful APIs
 * - Unit Testing
 */
@SpringBootApplication
public class SpringBootApplication {

    private static final Logger logger = LoggerFactory.getLogger(SpringBootApplication.class);

    public static void main(String[] args) {
        logger.info("Starting Spring Boot Application...");
        SpringApplication.run(SpringBootApplication.class, args);
        logger.info("Spring Boot Application started successfully!");
    }
}
