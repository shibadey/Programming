# Diagrams and Visual Documentation

## 1. Application Architecture Diagram

### Component Diagram

```
┌────────────────────────────────────────────────────────┐
│                   Spring Boot Application              │
│                                                         │
│  ┌──────────────────────────────────────────────────┐ │
│  │         HTTP Request from Client                 │ │
│  │  GET /api/v1/hello/greet?name=John             │ │
│  └──────────┬───────────────────────────────────────┘ │
│             │                                         │
│  ┌──────────▼───────────────────────────────────────┐ │
│  │    Spring DispatcherServlet (Front Controller)  │ │
│  │    - Route requests to appropriate handler      │ │
│  │    - Serialize/Deserialize responses            │ │
│  └──────────┬───────────────────────────────────────┘ │
│             │                                         │
│  ┌──────────▼───────────────────────────────────────┐ │
│  │  HelloWorldController (@RestController)         │ │
│  │  - Receive HTTP requests                        │ │
│  │  - Validate input parameters                    │ │
│  │  - Call service layer                           │ │
│  │  - Return HTTP responses                        │ │
│  │                                                  │ │
│  │  @GetMapping("/hello/greet")                    │ │
│  │  public ResponseEntity<HelloWorldResponse>      │ │
│  │      greetUser(@RequestParam String name) { }   │ │
│  └──────────┬───────────────────────────────────────┘ │
│             │                                         │
│  ┌──────────▼───────────────────────────────────────┐ │
│  │   HelloWorldService (@Service)                  │ │
│  │   - Implement business logic                    │ │
│  │   - Validate business rules                     │ │
│  │   - Create response objects                     │ │
│  │                                                  │ │
│  │   public HelloWorldResponse                     │ │
│  │       getGreetingMessage(String name) { }       │ │
│  └──────────┬───────────────────────────────────────┘ │
│             │                                         │
│  ┌──────────▼───────────────────────────────────────┐ │
│  │    DTOs (Data Transfer Objects)                 │ │
│  │    - HelloWorldResponse                         │ │
│  │    - ErrorResponse                              │ │
│  └──────────┬───────────────────────────────────────┘ │
│             │                                         │
│  ┌──────────▼───────────────────────────────────────┐ │
│  │   Serialization to JSON                         │ │
│  │   @Data, @Builder Annotations                   │ │
│  └──────────┬───────────────────────────────────────┘ │
│             │                                         │
│  ┌──────────▼───────────────────────────────────────┐ │
│  │    HTTP Response to Client                      │ │
│  │    200 OK                                        │ │
│  │    {"message": "Hello, John!", ...}             │ │
│  └─────────────────────────────────────────────────┘ │
│                                                         │
└────────────────────────────────────────────────────────┘
```

## 2. Request Flow Diagram

```
Client
   │
   │ GET /api/v1/hello/greet?name=John
   │
   ▼
┌─────────────────────────────┐
│ Spring DispatcherServlet    │
│ - Route to controller       │
└──────────┬──────────────────┘
           │
           ▼
┌─────────────────────────────┐
│ HelloWorldController        │
│ 1. Validate request         │
│ 2. Extract parameters       │
│ 3. Log incoming request     │
└──────────┬──────────────────┘
           │
           │ Call service
           │
           ▼
┌─────────────────────────────┐
│ HelloWorldService           │
│ 1. Validate name parameter  │
│ 2. Generate message         │
│ 3. Get current timestamp    │
│ 4. Build response DTO       │
│ 5. Log operation            │
└──────────┬──────────────────┘
           │
           │ Return DTO
           │
           ▼
┌─────────────────────────────┐
│ HelloWorldController        │
│ 1. Wrap in ResponseEntity   │
│ 2. Set HTTP status 200      │
│ 3. Log success              │
└──────────┬──────────────────┘
           │
           │ Serialize
           │
           ▼
┌─────────────────────────────┐
│ JSON Serialization          │
│ HelloWorldResponse → JSON   │
└──────────┬──────────────────┘
           │
           │ HTTP Response
           │
           ▼
Client
```

## 3. Error Handling Flow Diagram

```
Request Processing
   │
   ▼
┌──────────────────────────────┐
│ Controller or Service throws │
│ Exception                    │
└──────────┬───────────────────┘
           │
           │ Exception propagates up
           │
           ▼
┌──────────────────────────────┐
│ Spring Framework intercepts  │
│ exception                    │
└──────────┬───────────────────┘
           │
           ▼
┌──────────────────────────────┐
│ GlobalExceptionHandler       │
│ @RestControllerAdvice       │
│ - Find matching handler     │
│ - Log error with context    │
│ - Create ErrorResponse DTO  │
└──────────┬───────────────────┘
           │
           ▼
┌──────────────────────────────┐
│ Select HTTP Status           │
│ - 400 Bad Request            │
│ - 500 Internal Server Error  │
└──────────┬───────────────────┘
           │
           ▼
┌──────────────────────────────┐
│ Serialize ErrorResponse      │
│ Convert to JSON              │
└──────────┬───────────────────┘
           │
           ▼
┌──────────────────────────────┐
│ Send Error Response          │
│ HTTP Status + JSON Body      │
└──────────────────────────────┘
```

## 4. Class Hierarchy Diagram

```
┌─────────────────────────────────────────────────┐
│  Spring Framework Classes                       │
│                                                 │
│  RestController                                 │
│  ├── @RestController                           │
│  ├── Handles HTTP requests                      │
│  └── Returns serialized responses               │
└────────┬────────────────────────────────────────┘
         │ extends
         │
┌────────▼────────────────────────────────────────┐
│  HelloWorldController                           │
│                                                 │
│  - helloWorldService: HelloWorldService         │
│  + sayHello(): ResponseEntity                   │
│  + greetUser(name): ResponseEntity              │
└────────┬────────────────────────────────────────┘
         │ uses
         │
┌────────▼────────────────────────────────────────┐
│  HelloWorldService (@Service)                   │
│                                                 │
│  + getHelloMessage(): HelloWorldResponse        │
│  + getGreetingMessage(name): HelloWorldResponse │
└────────┬────────────────────────────────────────┘
         │ returns
         │
┌────────▼────────────────────────────────────────┐
│  HelloWorldResponse (DTO)                       │
│                                                 │
│  @Data @Builder                                 │
│  - message: String                              │
│  - timestamp: String                            │
│  - status: String                               │
└─────────────────────────────────────────────────┘

┌────────────────────────────────────────────────┐
│  GlobalExceptionHandler                        │
│  @RestControllerAdvice                         │
│                                                │
│  + handleIllegalArgumentException(ex)          │
│  + handleGlobalException(ex)                   │
└────────┬───────────────────────────────────────┘
         │ returns
         │
┌────────▼───────────────────────────────────────┐
│  ErrorResponse (DTO)                           │
│                                                │
│  @Data @Builder                                │
│  - timestamp: String                           │
│  - status: int                                 │
│  - error: String                               │
│  - message: String                             │
│  - path: String                                │
└────────────────────────────────────────────────┘
```

## 5. Sequence Diagram - Happy Path

```
Client          Controller          Service             DTO
  │                 │                  │                 │
  │ GET /hello/greet?name=John         │                 │
  │────────────────────────────────────>                 │
  │                 │                  │                 │
  │          Receive request           │                 │
  │                 │ Validate         │                 │
  │                 ├──Log request     │                 │
  │                 │                  │                 │
  │                 │ getGreetingMessage("John")        │
  │                 │──────────────────>                 │
  │                 │                  │                 │
  │                 │           Validate name            │
  │                 │           Generate message         │
  │                 │           Get timestamp            │
  │                 │           Build HelloWorldResponse │
  │                 │                  ├──────────────>  │
  │                 │                  │    Create DTO   │
  │                 │                  │<──────────────┤ │
  │                 │<──────────────────                 │
  │                 │                  │                 │
  │            Wrap in ResponseEntity  │                 │
  │                 │ Status 200 OK     │                 │
  │                 │ Log success       │                 │
  │                 │                   │                 │
  │ 200 OK JSON     │                   │                 │
  │<────────────────                    │                 │
  │                 │                   │                 │
```

## 6. Sequence Diagram - Error Path

```
Client          Controller          Service       ExceptionHandler
  │                 │                  │                 │
  │ GET /hello/greet?name=             │                 │
  │────────────────────────────────────>                 │
  │                 │                  │                 │
  │          Receive request           │                 │
  │                 │ Validate         │                 │
  │                 ├──Log request     │                 │
  │                 │                  │                 │
  │                 │ getGreetingMessage("")
  │                 │──────────────────>                 │
  │                 │                  │                 │
  │                 │           Name is empty            │
  │                 │           Throw IllegalArgument    │
  │                 │           Exception                │
  │                 │<──────────────────                 │
  │                 │                  │                 │
  │                 │ Exception caught │                 │
  │                 │───────────────────────────────────>│
  │                 │                  │                 │
  │                 │                  │          Find handler
  │                 │                  │          Log error
  │                 │                  │          Create ErrorResponse
  │                 │<───────────────────────────────────┤
  │                 │                  │                 │
  │ 400 Bad Request │                  │                 │
  │ Error JSON      │                  │                 │
  │<────────────────                   │                 │
  │                 │                  │                 │
```

## 7. Testing Structure Diagram

```
┌──────────────────────────────────────────────────────┐
│              Unit Tests                              │
│                                                       │
│  ┌────────────────────────────────────────────────┐ │
│  │  HelloWorldControllerTest                      │ │
│  │                                                │ │
│  │  @Mock: HelloWorldService                     │ │
│  │  @InjectMocks: HelloWorldController           │ │
│  │                                                │ │
│  │  @Test: testSayHello_Success()                │ │
│  │  @Test: testSayHello_Exception()              │ │
│  │  @Test: testGreetUser_Success()               │ │
│  │  @Test: testGreetUser_DefaultName()           │ │
│  └────────────────────────────────────────────────┘ │
│                                                       │
│  ┌────────────────────────────────────────────────┐ │
│  │  HelloWorldServiceTest                         │ │
│  │                                                │ │
│  │  HelloWorldService: Real instance             │ │
│  │                                                │ │
│  │  @Test: testGetHelloMessage_Success()         │ │
│  │  @Test: testGetGreetingMessage_Success()      │ │
│  │  @Test: testGetGreetingMessage_NullName()     │ │
│  │  @Test: testGetGreetingMessage_EmptyName()    │ │
│  │  @Test: testGetGreetingMessage_WhitespaceName()│ │
│  └────────────────────────────────────────────────┘ │
│                                                       │
└──────────────────────────────────────────────────────┘
```

## 8. Dependency Injection Diagram

```
Spring Container
┌──────────────────────────────────────────────┐
│                                              │
│  ┌─────────────────────────────────────┐   │
│  │  HelloWorldService                  │   │
│  │  @Service                           │   │
│  │  - Singleton scope                  │   │
│  └──────────────┬──────────────────────┘   │
│                 │                          │
│                 │ Inject                   │
│                 │                          │
│  ┌──────────────▼──────────────────────┐   │
│  │  HelloWorldController               │   │
│  │  @RestController                    │   │
│  │  @Autowired                         │   │
│  │  private HelloWorldService          │   │
│  └─────────────────────────────────────┘   │
│                                              │
└──────────────────────────────────────────────┘
```

## 9. Data Flow Diagram

```
┌─────────────────────────────────────────────────────┐
│              Input Data (Request)                   │
│  GET /api/v1/hello/greet?name=John                 │
└──────────────────┬──────────────────────────────────┘
                   │
                   ▼
         ┌─────────────────────┐
         │ Parameter Extraction��
         │ name = "John"       │
         └─────────┬───────────┘
                   │
                   ▼
         ┌─────────────────────┐
         │ Input Validation    │
         │ Check if empty/null │
         └─────────┬───────────┘
                   │ Valid
                   ▼
         ┌─────────────────────────────┐
         │ Business Logic Processing   │
         │ - Generate message          │
         │ - Get timestamp             │
         │ - Create response object    │
         └─────────┬───────────────────┘
                   │
                   ▼
         ┌─────────────────────┐
         │ DTO Creation        │
         │ HelloWorldResponse  │
         │ - message: String   │
         │ - timestamp: String │
         │ - status: String    │
         └─────────┬───────────┘
                   │
                   ▼
         ┌─────────────────────┐
         │ Serialization       │
         │ Object → JSON       │
         └─────────┬───────────┘
                   │
                   ▼
        ┌──────────────────────────┐
        │ Output Data (Response)   │
        │ {                        │
        │   "message": "..."      │
        │   "timestamp": "..."    │
        │   "status": "SUCCESS"   │
        │ }                        │
        └──────────────────────────┘
```

## 10. Logging Flow Diagram

```
┌──────────────────────────────────────────────┐
│            Application Execution             │
└──────────────────┬───────────────────────────┘
                   │
                   ▼
    ┌──────────────────────────────┐
    │ Logger.info(message)         │
    │ Logger.debug(message)        │
    │ Logger.error(message, ex)    │
    └──────────────┬────────────────┘
                   │
                   ▼
    ┌──────────────────────────────┐
    │ SLF4J (Logger Facade)        │
    │ - Abstract logging API       │
    └──────────────┬────────────────┘
                   │
                   ▼
    ┌──────────────────────────────┐
    │ Logback Implementation       │
    │ - Format log messages        │
    │ - Apply log levels           │
    │ - Route to appenders         │
    └──────────────┬────────────────┘
                   │
        ┌──────────┴──────────┐
        │                     │
        ▼                     ▼
    ┌────────────┐    ┌────────────┐
    │ Console    │    │ Log File   │
    │ Appender   │    │ Appender   │
    │            │    │            │
    │%d{...} -   │    │logs/app.log│
    │%msg%n     │    │            │
    └────────────┘    └────────────┘
```
