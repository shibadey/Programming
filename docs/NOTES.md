# Development Notes

## Project Overview

This is a Spring Boot application demonstrating best practices for:
- RESTful API development
- Logging and monitoring
- Exception handling
- Unit testing with JUnit 5
- Multi-module Maven project structure

## Key Implementation Details

### 1. Project Structure

```
spring-boot-app/
├── app/                          # Main application
│   └── src/
│       ├── main/
│       │   ├── java/
│       │   │   └── com/example/
│       │   │       ├── SpringBootApplication.java
│       │   │       ├── controller/
│       │   │       ├── service/
│       │   │       ├── dto/
│       │   │       └── exception/
│       │   └── resources/
│       │       └── application.properties
│       └── test/
│           └── java/
├── docs/                         # Documentation
└── pom.xml                       # Parent POM
```

### 2. Technology Choices

#### Why Java 21?
- Latest LTS version
- Better performance
- Modern language features
- Security patches
- Industry standard

#### Why Spring Boot 3.2.0?
- Stable version
- Good dependency management
- Extensive ecosystem
- Community support
- Production-ready

#### Why Lombok?
- Reduces boilerplate code
- Improves code readability
- Easy to maintain
- Builder pattern support
- Industry standard

### 3. Logging Implementation

#### Setup
```java
private static final Logger logger = LoggerFactory.getLogger(MyClass.class);
```

#### Log Levels
- **DEBUG**: Development-level details
- **INFO**: Business-important events
- **WARN**: Potentially problematic situations
- **ERROR**: Error conditions with context

#### Configuration in application.properties
```properties
logging.level.root=INFO
logging.level.com.example=DEBUG
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %msg%n
logging.file.name=logs/application.log
logging.file.max-size=10MB
logging.file.max-history=10
```

#### Logging Best Practices
1. Log at appropriate levels
2. Include context in log messages
3. Log errors with stack traces
4. Don't log sensitive information
5. Use structured logging where possible

### 4. Exception Handling

#### Global Exception Handler
```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception ex) {
        // Centralized handling
    }
}
```

#### Benefits
- Consistent error responses
- Centralized logging
- User-friendly error messages
- No error details leaked to client
- Stack traces only in logs

### 5. Testing Strategy

#### Unit Tests
- Test business logic in isolation
- Mock external dependencies
- Use descriptive test names with @DisplayName
- Follow AAA pattern (Arrange-Act-Assert)

#### Test Classes
1. **HelloWorldControllerTest**
   - Tests REST endpoints
   - Mocks service layer
   - Tests both success and error paths

2. **HelloWorldServiceTest**
   - Tests business logic
   - Tests validation
   - Tests edge cases

#### Running Tests
```bash
mvn test                          # Run all tests
mvn test -Dtest=ClassName        # Run specific test
mvn test -Dtest=*Controller*     # Run matching tests
```

### 6. REST API Design

#### Endpoint Naming Conventions
- Use nouns for resources: `/api/v1/hello`
- Use verbs only when necessary: `/hello/greet`
- Include version in URL: `/api/v1/`
- Use lowercase for paths
- Use hyphens for multi-word paths

#### HTTP Methods
- **GET**: Retrieve data
- **POST**: Create new resource
- **PUT**: Update existing resource
- **DELETE**: Remove resource
- **PATCH**: Partial update

#### Response Format
```json
{
  "message": "...",
  "timestamp": "...",
  "status": "..."
}
```

### 7. Code Organization

#### Package Structure
```
com.example
├── controller      # REST controllers
├── service         # Business logic
├── dto             # Data transfer objects
├── exception       # Exception handling
└── SpringBootApplication.java
```

#### Naming Conventions
- **Controller**: `[Resource]Controller.java`
- **Service**: `[Resource]Service.java`
- **DTO**: `[Resource]Response.java`, `[Resource]Request.java`
- **Exception**: `[Resource]Exception.java`

## Common Issues and Solutions

### Issue 1: Dependency Not Found
**Cause**: Maven cache is corrupted or dependencies not downloaded
**Solution**:
```bash
mvn clean
mvn dependency:resolve
```

### Issue 2: Port Already in Use
**Cause**: Another application using port 8080
**Solution**: Change port in application.properties
```properties
server.port=8081
```

### Issue 3: Tests Failing
**Cause**: Mock setup incorrect or missing dependencies
**Solution**: Verify @Mock and @InjectMocks setup in test class

## Performance Optimization

### Current State
- Stateless design enables horizontal scaling
- No external dependencies (database, cache)
- Lightweight response payloads

### Future Optimizations
1. Add caching layer (Redis, Memcached)
2. Implement database persistence
3. Add async processing
4. Implement response compression
5. Add monitoring and metrics

## Security Considerations

### Currently Implemented
- Input validation
- Safe error messages
- Exception handling without exposing internals

### Future Enhancements
1. Add authentication (JWT, OAuth)
2. Add authorization (roles, permissions)
3. Add request validation with Spring Validation
4. Add CORS configuration
5. Add API rate limiting
6. Add HTTPS support
7. Add security headers

## Deployment

### Local Development
```bash
mvn spring-boot:run
```

### Production
```bash
# Build executable JAR
mvn clean package

# Run JAR
java -jar app/target/app-1.0.0.jar

# With custom properties
java -jar app/target/app-1.0.0.jar --server.port=8080
```

### Docker
```dockerfile
FROM openjdk:21-slim
COPY app/target/app-1.0.0.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

## Monitoring

### Actuator Endpoints
- Health Check: `GET /actuator/health`
- Application Info: `GET /actuator/info`

### Log Files
- Location: `logs/application.log`
- Size: Max 10MB per file
- History: 10 previous files retained

## Future Enhancements

### Short Term
1. Add database connectivity
2. Add more comprehensive tests
3. Add API documentation (Swagger/OpenAPI)
4. Add health checks

### Medium Term
1. Add authentication/authorization
2. Add request validation
3. Add caching
4. Add async processing

### Long Term
1. Add distributed tracing
2. Add metrics collection
3. Add advanced monitoring
4. Add multi-datacenter support

## Useful Commands

### Maven Commands
```bash
mvn clean              # Clean build
mvn compile            # Compile source
mvn test               # Run tests
mvn package            # Build JAR
mvn install            # Install to local repository
mvn clean package      # Clean and build
mvn dependency:tree    # Show dependency tree
```

### Git Commands
```bash
git checkout spring-boot-app
git add .
git commit -m "message"
git push origin spring-boot-app
```

### Application Commands
```bash
curl http://localhost:8080/api/v1/hello
curl "http://localhost:8080/api/v1/hello/greet?name=John"
java -jar app/target/app-1.0.0.jar --help
```

## References

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Java 21 Documentation](https://docs.oracle.com/en/java/javase/21/)
- [JUnit 5 Documentation](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/)
- [Lombok Documentation](https://projectlombok.org/)
