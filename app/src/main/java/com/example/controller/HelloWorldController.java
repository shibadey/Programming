package com.example.controller;

import com.example.dto.HelloWorldResponse;
import com.example.service.HelloWorldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for Hello World API endpoints.
 * Demonstrates basic REST API creation with Spring Boot.
 */
@RestController
@RequestMapping("/api/v1/hello")
public class HelloWorldController {

    private static final Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

    @Autowired
    private HelloWorldService helloWorldService;

    /**
     * Simple Hello World endpoint.
     *
     * @return ResponseEntity with HelloWorldResponse
     */
    @GetMapping
    public ResponseEntity<HelloWorldResponse> sayHello() {
        logger.info("Received request for /api/v1/hello");
        try {
            HelloWorldResponse response = helloWorldService.getHelloMessage();
            logger.info("Successfully processed hello world request");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error processing hello world request", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Hello endpoint with a name parameter.
     *
     * @param name the name to greet
     * @return ResponseEntity with HelloWorldResponse
     */
    @GetMapping("/greet")
    public ResponseEntity<HelloWorldResponse> greetUser(
            @RequestParam(value = "name", defaultValue = "World") String name) {
        logger.info("Received greet request for name: {}", name);
        try {
            if (name == null || name.trim().isEmpty()) {
                logger.warn("Empty name provided, using default");
                name = "World";
            }
            HelloWorldResponse response = helloWorldService.getGreetingMessage(name);
            logger.info("Successfully processed greet request for: {}", name);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error processing greet request", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
