package com.example.controller;

import com.example.dto.HelloWorldResponse;
import com.example.service.HelloWorldService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit Test Cases for HelloWorldController.
 * Tests REST endpoint functionality and error handling.
 */
@DisplayName("HelloWorldController Tests")
public class HelloWorldControllerTest {

    @Mock
    private HelloWorldService helloWorldService;

    @InjectMocks
    private HelloWorldController helloWorldController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test sayHello endpoint - Success Case")
    public void testSayHello_Success() {
        // Arrange
        HelloWorldResponse response = HelloWorldResponse.builder()
                .message("Hello, World!")
                .timestamp("2024-01-01 10:00:00")
                .status("SUCCESS")
                .build();
        when(helloWorldService.getHelloMessage()).thenReturn(response);

        // Act
        ResponseEntity<HelloWorldResponse> result = helloWorldController.sayHello();

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Hello, World!", result.getBody().getMessage());
        assertEquals("SUCCESS", result.getBody().getStatus());
        verify(helloWorldService, times(1)).getHelloMessage();
    }

    @Test
    @DisplayName("Test sayHello endpoint - Exception Case")
    public void testSayHello_Exception() {
        // Arrange
        when(helloWorldService.getHelloMessage()).thenThrow(new RuntimeException("Service error"));

        // Act
        ResponseEntity<HelloWorldResponse> result = helloWorldController.sayHello();

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        verify(helloWorldService, times(1)).getHelloMessage();
    }

    @Test
    @DisplayName("Test greetUser endpoint - Success Case")
    public void testGreetUser_Success() {
        // Arrange
        String name = "John";
        HelloWorldResponse response = HelloWorldResponse.builder()
                .message("Hello, John!")
                .timestamp("2024-01-01 10:00:00")
                .status("SUCCESS")
                .build();
        when(helloWorldService.getGreetingMessage(name)).thenReturn(response);

        // Act
        ResponseEntity<HelloWorldResponse> result = helloWorldController.greetUser(name);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Hello, John!", result.getBody().getMessage());
        verify(helloWorldService, times(1)).getGreetingMessage(name);
    }

    @Test
    @DisplayName("Test greetUser endpoint - Default Name")
    public void testGreetUser_DefaultName() {
        // Arrange
        HelloWorldResponse response = HelloWorldResponse.builder()
                .message("Hello, World!")
                .timestamp("2024-01-01 10:00:00")
                .status("SUCCESS")
                .build();
        when(helloWorldService.getGreetingMessage("World")).thenReturn(response);

        // Act
        ResponseEntity<HelloWorldResponse> result = helloWorldController.greetUser("World");

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Hello, World!", result.getBody().getMessage());
    }
}
