# API Documentation

## Base URL

```
http://localhost:8080/api/v1
```

## API Endpoints

### 1. Hello World Endpoint

#### Endpoint
```
GET /hello
```

#### Description
Returns a simple "Hello, World!" greeting message.

#### Request
```
GET /api/v1/hello HTTP/1.1
Host: localhost:8080
```

#### Response

**Success Response (200 OK):**
```json
{
  "message": "Hello, World!",
  "timestamp": "2024-01-15 10:30:45",
  "status": "SUCCESS"
}
```

**Response Headers:**
```
Content-Type: application/json
Content-Length: 92
```

#### cURL Example
```bash
curl -X GET http://localhost:8080/api/v1/hello
```

#### Response Codes
- `200 OK`: Successful request
- `500 Internal Server Error`: Server-side error

---

### 2. Personalized Greeting Endpoint

#### Endpoint
```
GET /hello/greet
```

#### Description
Returns a personalized greeting message for the specified name.

#### Request

**URL Parameters:**
- `name` (optional, string, default: "World") - The name to greet

**Request Examples:**
```
GET /api/v1/hello/greet HTTP/1.1
Host: localhost:8080
```

OR with parameter:
```
GET /api/v1/hello/greet?name=John HTTP/1.1
Host: localhost:8080
```

#### Response

**Success Response (200 OK):**
```json
{
  "message": "Hello, John!",
  "timestamp": "2024-01-15 10:35:20",
  "status": "SUCCESS"
}
```

**Error Response (400 Bad Request) - When name is empty/null:**
```json
{
  "timestamp": "2024-01-15 10:35:20",
  "status": 400,
  "error": "Bad Request",
  "message": "Name cannot be null or empty",
  "path": "/api/v1/hello/greet"
}
```

**Error Response (500 Internal Server Error):**
```json
{
  "timestamp": "2024-01-15 10:35:20",
  "status": 500,
  "error": "Internal Server Error",
  "message": "An unexpected error occurred",
  "path": "/api/v1/hello/greet"
}
```

#### cURL Examples

**Default greeting:**
```bash
curl -X GET http://localhost:8080/api/v1/hello/greet
```

**Personalized greeting:**
```bash
curl -X GET "http://localhost:8080/api/v1/hello/greet?name=Alice"
```

**With multiple parameters:**
```bash
curl -X GET "http://localhost:8080/api/v1/hello/greet?name=Bob&other=value"
```

#### Response Codes
- `200 OK`: Successful request
- `400 Bad Request`: Invalid input (empty/null name after processing)
- `500 Internal Server Error`: Server-side error

#### Response Fields
- `message` (string): The greeting message
- `timestamp` (string): When the message was generated (format: yyyy-MM-dd HH:mm:ss)
- `status` (string): Status of the request ("SUCCESS" or error details)

---

## Error Handling

### Error Response Format

All errors follow a consistent format:

```json
{
  "timestamp": "2024-01-15 10:35:20",
  "status": 400,
  "error": "Bad Request",
  "message": "Detailed error message",
  "path": "/api/v1/hello/greet"
}
```

### Error Types

#### 400 Bad Request
- Invalid input parameters
- Empty or null values where not allowed
- Malformed requests

**Example:**
```json
{
  "timestamp": "2024-01-15 10:35:20",
  "status": 400,
  "error": "Bad Request",
  "message": "Name cannot be null or empty",
  "path": "/api/v1/hello/greet"
}
```

#### 500 Internal Server Error
- Unexpected server-side errors
- Unhandled exceptions
- Resource unavailability

**Example:**
```json
{
  "timestamp": "2024-01-15 10:35:20",
  "status": 500,
  "error": "Internal Server Error",
  "message": "An unexpected error occurred",
  "path": "/api/v1/hello"
}
```

---

## Content Type

- **Request Content-Type**: `application/json` (for POST/PUT requests if any)
- **Response Content-Type**: `application/json`

---

## Authentication

Currently, no authentication is required for these endpoints.

### Future Enhancements
- API Key authentication
- JWT token-based authentication
- OAuth 2.0 integration

---

## Rate Limiting

No rate limiting is currently implemented.

### Future Enhancements
- Implement rate limiting per IP/user
- Add request throttling
- Track API usage metrics

---

## Pagination

Not applicable to current endpoints as they return single responses.

---

## Versioning

The API uses URL-based versioning.

- Current Version: `v1`
- Format: `/api/v1/...`

### Version Compatibility
- New endpoints will be added to new versions
- Breaking changes will bump the major version
- Backward compatibility maintained within same version

---

## Filtering

Not applicable to current endpoints.

---

## Sorting

Not applicable to current endpoints.

---

## Request/Response Examples

### Example 1: Simple Hello World

**Request:**
```bash
GET /api/v1/hello HTTP/1.1
Host: localhost:8080
Accept: application/json
```

**Response:**
```bash
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 92

{
  "message": "Hello, World!",
  "timestamp": "2024-01-15 10:30:45",
  "status": "SUCCESS"
}
```

### Example 2: Personalized Greeting

**Request:**
```bash
GET /api/v1/hello/greet?name=Sarah HTTP/1.1
Host: localhost:8080
Accept: application/json
```

**Response:**
```bash
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 92

{
  "message": "Hello, Sarah!",
  "timestamp": "2024-01-15 10:35:20",
  "status": "SUCCESS"
}
```

### Example 3: Error - Empty Name

**Request:**
```bash
GET /api/v1/hello/greet?name= HTTP/1.1
Host: localhost:8080
Accept: application/json
```

**Response:**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json
Content-Length: 180

{
  "timestamp": "2024-01-15 10:35:20",
  "status": 400,
  "error": "Bad Request",
  "message": "Name cannot be null or empty",
  "path": "/api/v1/hello/greet"
}
```

---

## HTTP Status Codes

| Code | Description |
|------|-------------|
| 200 | OK - Request successful |
| 400 | Bad Request - Invalid input |
| 500 | Internal Server Error - Server error |

---

## Best Practices

### For Clients
1. Always check HTTP status codes
2. Handle error responses appropriately
3. Implement retry logic for 5xx errors
4. Cache responses when appropriate
5. Use meaningful timeouts

### For Servers
1. Return appropriate HTTP status codes
2. Include detailed error messages
3. Log all requests and errors
4. Validate all inputs
5. Document all endpoints

---

## Changelog

### Version 1.0.0 (Current)
- Initial release
- Implemented `/hello` endpoint
- Implemented `/hello/greet` endpoint
- Global exception handling
- Comprehensive logging
