# System Architecture

## Overview

This document describes the architectural design of the Spring Boot Application.

## Architecture Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                        Client (Browser/API Client)          │
└──────────────────────────┬──────────────────────────────────┘
                           │
                    HTTP Request/Response
                           │
┌──────────────────────────▼──────────────────────────────────┐
│                    Spring Boot Application                  │
│                                                               │
│  ┌────────────────────────────────────────────────────────┐ │
│  │               REST Controller Layer                    │ │
│  │                                                         │ │
│  │  ┌──────────────┐  ┌──────────────┐                  │ │
│  │  │ HelloWorld   │  │ Other        │                  │ │
│  │  │ Controller   │  │ Controllers  │                  │ │
│  │  └──────────────┘  └──────────────┘                  │ │
│  │         │                                             │ │
│  │         ▼                                             │ │
│  ├────────────────────────────────────────────────────────┤ │
│  │              Service Layer                            │ │
│  │                                                         │ │
│  │  ┌──────────────┐  ┌──────────────┐                  │ │
│  │  │ HelloWorld   │  │ Other        │                  │ │
│  │  │ Service      │  │ Services     │                  │ │
│  │  └──────────────┘  └──────────────┘                  │ │
│  │         │                                             │ │
│  │         ▼                                             │ │
│  ├────────────────────────────────────────────────────────┤ │
│  │           Data Transfer Objects (DTOs)               │ │
│  │                                                         │ │
│  │  ┌──────────────────────────────────────┐            │ │
│  │  │ HelloWorldResponse, ErrorResponse    │            │ │
│  │  └──────────────────────────────────────┘            │ │
│  └────────────────────────────────────────────────────────┘ │
│                          │                                    │
│                          ▼                                    │
│  ┌────────────────────────────────────────────────────────┐ │
│  │          Exception Handling (Global Handler)          │ │
│  └────────────────────────────────────────────────────────┘ │
│                          │                                    │
│                    Error Response                             │
│                                                               │
└─────────────────────────────────────────────────────────────┘
                           │
                    HTTP Error Response
                           │
┌──────────────────────────▼──────────────────────────────────┐
│                    Client receives response                 │
└─────────────────────────────────────────────────────────────┘
```

## Layered Architecture

The application follows a 3-tier layered architecture:

### 1. Controller Layer (Presentation)

**Responsibility**: Handle HTTP requests and responses

- Receives incoming HTTP requests
- Validates input parameters
- Calls appropriate service methods
- Returns HTTP responses with appropriate status codes
- Handles request/response serialization

**Components**:
- `HelloWorldController`: REST endpoints for hello world functionality

**Design Pattern**: Model-View-Controller (MVC)

### 2. Service Layer (Business Logic)

**Responsibility**: Implement business logic and calculations

- Performs core business operations
- Applies business rules and validations
- Orchestrates multiple operations
- Handles data transformations
- Contains domain-specific logic

**Components**:
- `HelloWorldService`: Business logic for greeting messages

**Design Pattern**: Service Locator / Dependency Injection

### 3. Data Transfer Layer (Data)

**Responsibility**: Define data structures for communication

- Defines request/response formats
- Provides DTOs for API contracts
- Ensures type safety
- Facilitates serialization/deserialization

**Components**:
- `HelloWorldResponse`: Response DTO
- `ErrorResponse`: Error response DTO

## Cross-Cutting Concerns

### Logging

- **Framework**: SLF4J with Logback
- **Strategy**: Centralized logging at service and controller levels
- **Log Levels**: DEBUG, INFO, WARN, ERROR

### Exception Handling

- **Framework**: Spring's `@RestControllerAdvice`
- **Strategy**: Global exception handler catches all exceptions
- **Approach**: Centralized error response formatting

## Design Patterns Used

### 1. Dependency Injection

```java
@Autowired
private HelloWorldService helloWorldService;
```

Benefits:
- Loose coupling
- Easy testing
- Configuration management

### 2. Service Pattern

Business logic separated from controllers:
- Reusability
- Testability
- Maintainability

### 3. DTO (Data Transfer Object)

Used to transfer data between layers:
- Clear API contracts
- Decoupling from internal entities
- Type safety

### 4. Exception Wrapper

Custom error responses using `ErrorResponse` DTO:
- Consistent error format
- User-friendly messages
- Traceable requests

## Request Flow

```
1. Client sends HTTP request
   └─> GET /api/v1/hello/greet?name=John

2. DispatcherServlet routes to HelloWorldController
   └─> @GetMapping("/greet")
   └─> greetUser(String name)

3. Controller validates input
   └─> Check if name is null/empty
   └─> Log request details

4. Controller calls HelloWorldService
   └─> helloWorldService.getGreetingMessage(name)

5. Service executes business logic
   └─> Generate greeting message
   └─> Create timestamp
   └─> Build response DTO
   └─> Log operation

6. Service returns HelloWorldResponse
   └─> Response: {message, timestamp, status}

7. Controller wraps response in ResponseEntity
   └─> Status: HTTP 200 OK
   └─> Body: HelloWorldResponse

8. DispatcherServlet sends HTTP response
   └─> Content-Type: application/json
   └─> Response body serialized as JSON

9. Client receives response
```

## Error Handling Flow

```
1. Exception occurs in Controller or Service
   └─> IllegalArgumentException
   └─> RuntimeException
   └─> Any other Exception

2. GlobalExceptionHandler intercepts exception
   └─> @ExceptionHandler annotation matches
   └─> Appropriate handler method called

3. Handler creates ErrorResponse
   └─> Timestamp
   └─> HTTP Status
   └─> Error details
   └─> Request path

4. Handler logs the error
   └─> Error level logging
   └─> Stack trace included

5. ErrorResponse sent to client
   └─> HTTP Status Code
   └─> JSON formatted error details

6. Client receives error response
```

## Technology Stack

### Frameworks
- **Spring Boot 3.2.0**: Application framework
- **Spring Web**: REST support
- **Spring Logging**: SLF4J logging

### Languages
- **Java 21**: Programming language

### Build Tools
- **Maven 3.x**: Build and dependency management

### Testing
- **JUnit 5**: Testing framework
- **Mockito**: Mocking library
- **Spring Boot Test**: Integration testing

### Utilities
- **Lombok**: Boilerplate reduction (getters, setters, builders)
- **Logback**: Logging implementation

## Scalability Considerations

### Horizontal Scaling
- Stateless design allows multiple instances
- Load balancer can distribute requests
- No session affinity required

### Performance Optimization
- Caching can be added at service layer
- Connection pooling for databases
- Async processing for long-running tasks

### Monitoring
- Structured logging for analysis
- Spring Actuator endpoints for health checks
- Metrics collection support

## Security Considerations

- Input validation on all endpoints
- Error messages don't expose internal details
- Exception stack traces logged but not sent to client
- CORS configuration can be added
- API authentication can be implemented
