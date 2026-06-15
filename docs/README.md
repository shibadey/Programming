# Spring Boot Application - Documentation

## Overview

This is a comprehensive Spring Boot application demonstrating best practices including:
- **Logging**: Structured logging using SLF4J
- **Exception Handling**: Global exception handler with custom error responses
- **REST APIs**: RESTful endpoint design
- **Unit Testing**: Complete test coverage with JUnit 5 and Mockito
- **Java 21**: Modern Java version support

## Project Structure

```
spring-boot-app/
├── app/                    # Main application module
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/
│   │   │   │   ├── SpringBootApplication.java
│   │   │   │   ├── controller/
│   │   │   │   ├── service/
│   │   │   │   ├── dto/
│   │   │   │   └── exception/
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   │       └── java/com/example/
│   └── pom.xml
├── docs/                   # Documentation module
│   ├── README.md
│   ├── ARCHITECTURE.md
│   ├── DESIGN_DECISIONS.md
│   ├── API_DOCUMENTATION.md
│   ├── NOTES.md
│   ├── DIAGRAMS.md
│   └── pom.xml
└── pom.xml                 # Parent POM
```

## Getting Started

### Prerequisites
- Java 21 or higher
- Maven 3.8.0 or higher

### Building the Project

```bash
# Clone the repository
git clone https://github.com/shibadey/Programming.git
cd Programming
git checkout spring-boot-app

# Build the entire project
mvn clean package

# Build only the app module
cd app
mvn clean package
```

### Running the Application

```bash
# From the app directory
cd app
mvn spring-boot:run

# Or run the JAR file
java -jar target/app-1.0.0.jar
```

The application will start on `http://localhost:8080`

## API Endpoints

### Hello World Endpoint

#### GET /api/v1/hello
Returns a simple "Hello, World!" message.

**Response:**
```json
{
  "message": "Hello, World!",
  "timestamp": "2024-01-15 10:30:45",
  "status": "SUCCESS"
}
```

#### GET /api/v1/hello/greet
Returns a personalized greeting message.

**Query Parameters:**
- `name` (optional, default: "World") - The name to greet

**Example:**
```
GET /api/v1/hello/greet?name=John
```

**Response:**
```json
{
  "message": "Hello, John!",
  "timestamp": "2024-01-15 10:30:45",
  "status": "SUCCESS"
}
```

### Error Response Format

When an error occurs, the API returns an error response:

```json
{
  "timestamp": "2024-01-15 10:30:45",
  "status": 400,
  "error": "Bad Request",
  "message": "Name cannot be null or empty",
  "path": "/api/v1/hello/greet"
}
```

## Testing

### Running Tests

```bash
# Run all tests
mvn test

# Run tests with coverage
mvn test -DargLine='-javaagent:target/jacoco-runtime.jar'

# Run specific test class
mvn test -Dtest=HelloWorldControllerTest
```

### Test Cases Included

1. **HelloWorldControllerTest**
   - `testSayHello_Success`: Tests successful hello endpoint
   - `testSayHello_Exception`: Tests exception handling
   - `testGreetUser_Success`: Tests personalized greeting
   - `testGreetUser_DefaultName`: Tests default parameter

2. **HelloWorldServiceTest**
   - `testGetHelloMessage_Success`: Tests hello message generation
   - `testGetGreetingMessage_Success`: Tests personalized message
   - `testGetGreetingMessage_NullName`: Tests null validation
   - `testGetGreetingMessage_EmptyName`: Tests empty validation
   - `testGetGreetingMessage_WhitespaceName`: Tests whitespace validation

## Logging

The application uses SLF4J with Logback for logging.

### Configuration

Logging is configured in `application.properties`:

```properties
logging.level.root=INFO
logging.level.com.example=DEBUG
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %msg%n
logging.file.name=logs/application.log
logging.file.max-size=10MB
logging.file.max-history=10
```

### Log Levels

- **DEBUG**: Detailed information about the application flow
- **INFO**: General informational messages
- **WARN**: Warning messages for potential issues
- **ERROR**: Error messages with stack traces

## Exception Handling

The application implements a global exception handler (`GlobalExceptionHandler`) that catches and handles:

1. **IllegalArgumentException**: Returns 400 Bad Request
2. **General Exception**: Returns 500 Internal Server Error

Custom error responses include:
- Timestamp
- HTTP Status Code
- Error Type
- Error Message
- Request Path

## Dependencies

### Core Dependencies
- **spring-boot-starter-web**: Web and REST support
- **spring-boot-starter-logging**: Logging support
- **lombok**: Boilerplate reduction

### Test Dependencies
- **spring-boot-starter-test**: Testing framework
- **junit-jupiter**: JUnit 5 framework
- **mockito**: Mocking library

## Configuration

### Application Properties

```properties
# Server
server.port=8080
server.servlet.context-path=/

# Application
spring.application.name=Spring Boot Application

# Logging
logging.level.root=INFO
logging.level.com.example=DEBUG
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %msg%n
logging.file.name=logs/application.log
```

## Documentation Files

- **ARCHITECTURE.md**: System architecture and design patterns
- **DESIGN_DECISIONS.md**: Key design decisions and rationale
- **API_DOCUMENTATION.md**: Detailed API documentation
- **NOTES.md**: Additional notes and observations
- **DIAGRAMS.md**: Visual diagrams and flowcharts

## Contributing

Please follow these guidelines:
1. Write unit tests for all new features
2. Maintain consistent logging practices
3. Handle exceptions appropriately
4. Update documentation
5. Follow Java naming conventions

## License

This project is licensed under the MIT License.

## Contact

For questions or support, please contact the project maintainers.
