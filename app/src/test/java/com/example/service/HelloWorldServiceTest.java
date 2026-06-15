package com.example.service;

import com.example.dto.HelloWorldResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test Cases for HelloWorldService.
 * Tests business logic and error handling.
 */
@DisplayName("HelloWorldService Tests")
public class HelloWorldServiceTest {

    private HelloWorldService helloWorldService;

    @BeforeEach
    public void setUp() {
        helloWorldService = new HelloWorldService();
    }

    @Test
    @DisplayName("Test getHelloMessage - Success Case")
    public void testGetHelloMessage_Success() {
        // Act
        HelloWorldResponse response = helloWorldService.getHelloMessage();

        // Assert
        assertNotNull(response);
        assertEquals("Hello, World!", response.getMessage());
        assertEquals("SUCCESS", response.getStatus());
        assertNotNull(response.getTimestamp());
    }

    @Test
    @DisplayName("Test getGreetingMessage - Success Case")
    public void testGetGreetingMessage_Success() {
        // Arrange
        String name = "Alice";

        // Act
        HelloWorldResponse response = helloWorldService.getGreetingMessage(name);

        // Assert
        assertNotNull(response);
        assertEquals("Hello, Alice!", response.getMessage());
        assertEquals("SUCCESS", response.getStatus());
        assertNotNull(response.getTimestamp());
    }

    @Test
    @DisplayName("Test getGreetingMessage - Null Name")
    public void testGetGreetingMessage_NullName() {
        // Assert & Act
        assertThrows(IllegalArgumentException.class, () -> {
            helloWorldService.getGreetingMessage(null);
        });
    }

    @Test
    @DisplayName("Test getGreetingMessage - Empty Name")
    public void testGetGreetingMessage_EmptyName() {
        // Assert & Act
        assertThrows(IllegalArgumentException.class, () -> {
            helloWorldService.getGreetingMessage("");
        });
    }

    @Test
    @DisplayName("Test getGreetingMessage - Whitespace Name")
    public void testGetGreetingMessage_WhitespaceName() {
        // Assert & Act
        assertThrows(IllegalArgumentException.class, () -> {
            helloWorldService.getGreetingMessage("   ");
        });
    }
}
