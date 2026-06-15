package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Hello World API responses.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HelloWorldResponse {
    private String message;
    private String timestamp;
    private String status;
}
