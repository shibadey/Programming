package com.example.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Error Response DTO for exception handling.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
