# Design Decisions

## Overview

This document explains the key design decisions made during the development of the Spring Boot Application.

## 1. Layered Architecture

### Decision
Adopt a 3-tier layered architecture (Controller → Service → DTO)

### Rationale
- **Separation of Concerns**: Each layer has distinct responsibilities
- **Testability**: Layers can be tested independently
- **Maintainability**: Changes in one layer don't affect others
- **Scalability**: Easy to add new features without affecting existing code
- **Industry Standard**: Widely used and understood pattern

### Alternatives Considered
- **Monolithic**: All code in one layer (rejected for scalability)
- **Event-Driven**: Overkill for this application size
- **Repository Pattern**: Not needed without database layer

## 2. Dependency Injection

### Decision
Use Spring's @Autowired annotation for dependency injection

### Rationale
- **Loose Coupling**: Components depend on abstractions, not implementations
- **Testability**: Easy to inject mocks for testing
- **Configuration**: Dependencies can be configured externally
- **Built-in**: Spring handles all dependency management

### Code Example
```java
@RestController
public class HelloWorldController {
    @Autowired
    private HelloWorldService helloWorldService;
}
```

### Alternatives Considered
- **Constructor Injection**: More explicit but verbose
- **Manual Injection**: More control but more boilerplate

## 3. Global Exception Handling

### Decision
Implement centralized exception handling using @RestControllerAdvice

### Rationale
- **Consistency**: All errors have uniform format
- **Maintainability**: Exception handling in one place
- **DRY Principle**: No repetition of error handling code
- **User Experience**: Standardized error messages
- **Logging**: Centralized error logging

### Code Example
```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception ex) {
        // Centralized error handling
    }
}
```

### Alternatives Considered
- **Try-Catch blocks**: Scattered exception handling (rejected)
- **Error codes**: Less informative (rejected)

## 4. Logging Strategy

### Decision
Use SLF4J with structured logging at controller and service levels

### Rationale
- **Flexibility**: SLF4J is a facade allowing implementation changes
- **Performance**: Lazy logging with isDebugEnabled() checks
- **Traceability**: Track requests through the system
- **Debugging**: Understand application behavior
- **Monitoring**: Analyze logs for insights

### Log Levels Used
- **DEBUG**: Detailed flow information for development
- **INFO**: Important business events
- **WARN**: Potentially problematic situations
- **ERROR**: Error conditions with stack traces

### Code Example
```java
private static final Logger logger = LoggerFactory.getLogger(MyClass.class);
logger.debug("Debug message");
logger.info("Info message");
logger.error("Error message", exception);
```

## 5. Data Transfer Objects (DTOs)

### Decision
Use DTOs for request/response communication

### Rationale
- **API Contract**: Clear interface between layers
- **Decoupling**: Internal entities not exposed to clients
- **Flexibility**: Can evolve DTO independently
- **Validation**: Input validation at boundaries
- **Performance**: Only required fields transmitted

### Code Example
```java
@Data
@Builder
public class HelloWorldResponse {
    private String message;
    private String timestamp;
    private String status;
}
```

### Alternatives Considered
- **Entity-based**: Too tightly coupled (rejected)
- **Map-based**: Type unsafe (rejected)

## 6. Java 21 Version

### Decision
Target Java 21 as minimum version

### Rationale
- **Modern Features**: Records, pattern matching, virtual threads
- **Performance**: Improvements and optimizations
- **LTS Status**: Long-term support and stability
- **Security**: Latest security patches
- **Future-proof**: Align with industry standards

### Features Utilized
- **Modern Maven Configuration**: Java 21 compiler settings
- **Latest Spring Boot**: Compatible with Java 21

## 7. Testing Strategy

### Decision
Use JUnit 5 + Mockito for unit testing with @DisplayName annotations

### Rationale
- **Readability**: @DisplayName makes test purpose clear
- **Isolation**: Mockito isolates units for true unit tests
- **Framework**: JUnit 5 is the current standard
- **Expressiveness**: Fluent assertions with clear failures
- **Coverage**: Test both success and error paths

### Test Structure
```java
@DisplayName("Service Tests")
public class HelloWorldServiceTest {
    @Test
    @DisplayName("Should generate greeting message successfully")
    public void testGetGreetingMessage_Success() {
        // Arrange
        // Act
        // Assert
    }
}
```

### Test Coverage Goals
- Happy path scenarios
- Error conditions
- Edge cases (null, empty, whitespace)
- Exception handling

## 8. REST API Design

### Decision
Follow REST conventions with clear endpoint naming

### Rationale
- **Predictability**: Developers can guess endpoints
- **Standard**: Follows HTTP methods correctly
- **Versioning**: Include API version in path (/api/v1/)
- **Resource-oriented**: Endpoints represent resources

### Endpoint Design
- **Base Path**: `/api/v1/` for versioning
- **Resource**: `/hello` for hello world functionality
- **Actions**: `/greet` for specific operation
- **Parameters**: Query params for optional data

### Alternatives Considered
- **No versioning**: Makes breaking changes difficult (rejected)
- **RPC-style**: Less RESTful (rejected)

## 9. Lombok Usage

### Decision
Use Lombok for boilerplate reduction

### Rationale
- **Cleaner Code**: Less boilerplate (getters, setters, constructors)
- **Maintainability**: Less code to maintain
- **Builder Pattern**: @Builder simplifies object creation
- **Data Class**: @Data generates equals, hashCode, toString

### Code Example
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HelloWorldResponse {
    private String message;
    private String timestamp;
    private String status;
}
```

### Alternatives Considered
- **Records**: Not used for compatibility with services
- **Manual code**: Too verbose (rejected)

## 10. Multi-Module Project Structure

### Decision
Separate application code and documentation into separate Maven modules

### Rationale
- **Organization**: Clear separation of concerns
- **Independence**: Each module can be built separately
- **Scalability**: Easy to add more modules
- **Documentation**: Keeps docs alongside code
- **Reusability**: Modules can be used independently

### Structure
```
spring-boot-app/ (parent)
├── app/ (application module)
├── docs/ (documentation module)
└── pom.xml (parent POM)
```

### Alternatives Considered
- **Single module**: Less organized (rejected)
- **Separate repos**: Harder to maintain (rejected)

## 11. Properties-based Configuration

### Decision
Use application.properties for configuration instead of application.yml

### Rationale
- **Simplicity**: Flat structure is easier to read
- **Compatibility**: Works with all versions of Spring
- **Convention**: Widely adopted
- **Tool Support**: Better IDE support in most tools

### Configuration Areas
- Server configuration (port, context path)
- Logging configuration (levels, patterns)
- Actuator configuration (endpoints)

## 12. Input Validation

### Decision
Implement validation at controller and service levels

### Rationale
- **Early Detection**: Catch errors at boundaries
- **User Feedback**: Clear error messages
- **Security**: Prevent invalid data processing
- **Logging**: Track validation failures

### Validation Approach
- Null checks
- Empty/whitespace checks
- Type validation
- Business logic validation

## Summary

These design decisions prioritize:
1. **Maintainability**: Clear structure and separation of concerns
2. **Testability**: Mockable dependencies and isolated components
3. **Scalability**: Layered architecture for future growth
4. **Best Practices**: Industry-standard patterns and tools
5. **Developer Experience**: Clear, understandable code structure
