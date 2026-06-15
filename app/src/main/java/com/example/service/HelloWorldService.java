package com.example.service;

import com.example.dto.HelloWorldResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Service class for Hello World business logic.
 * Handles the core business logic and logging.
 */
@Service
public class HelloWorldService {

    private static final Logger logger = LoggerFactory.getLogger(HelloWorldService.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Get the hello world message.
     *
     * @return HelloWorldResponse with message
     */
    public HelloWorldResponse getHelloMessage() {
        logger.debug("Getting hello world message");
        String message = "Hello, World!";
        LocalDateTime timestamp = LocalDateTime.now();
        logger.info("Generated message: {} at {}", message, timestamp);
        
        return HelloWorldResponse.builder()
                .message(message)
                .timestamp(timestamp.format(formatter))
                .status("SUCCESS")
                .build();
    }

    /**
     * Get a personalized greeting message.
     *
     * @param name the name to greet
     * @return HelloWorldResponse with personalized message
     */
    public HelloWorldResponse getGreetingMessage(String name) {
        logger.debug("Getting greeting message for: {}", name);
        
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        
        String message = String.format("Hello, %s!", name);
        LocalDateTime timestamp = LocalDateTime.now();
        logger.info("Generated personalized message: {} at {}", message, timestamp);
        
        return HelloWorldResponse.builder()
                .message(message)
                .timestamp(timestamp.format(formatter))
                .status("SUCCESS")
                .build();
    }
}
