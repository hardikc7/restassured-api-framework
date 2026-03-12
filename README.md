# RestAssured API Automation Framework

Production-grade REST API test automation framework built with RestAssured and TestNG.

## Tech Stack
- Java 17
- RestAssured 5.4.0
- TestNG 7.9.0
- Maven
- Jackson 2.16.1
- JSON Simple

## Framework Architecture
```
src/
├── main/java/com/sdet/
│   └── utils/
│       └── ConfigReader.java      # Reads config.properties
└── test/java/com/sdet/
    └── api/
        ├── GetUserTest.java       # GET request tests
        ├── CreateUserTest.java    # POST request tests
        ├── UpdateUserTest.java    # PUT request tests
        └── DeleteUserTest.java    # DELETE request tests
```

## Key Features
- **given/when/then pattern** — readable, maintainable API tests
- **External config** — baseUrl in config.properties
- **Full CRUD coverage** — GET, POST, PUT, DELETE
- **Response validation** — status codes, body fields, headers
- **Response time assertions** — performance threshold checks
- **JSONPath extraction** — extract and reuse response values

## Setup & Run

### Prerequisites
- Java 17+
- Maven 3.6+

### Clone and Run
```bash
git clone https://github.com/hardikc7/restassured-api-framework.git
cd restassured-api-framework
mvn test
```

## Test Results
```
Tests run: 13
Failures:  0
Errors:    0
```

## Test Scenarios

### GET Tests
| Test | Endpoint | Assertion | Status |
|------|----------|-----------|--------|
| testGetSingleUser | GET /users/1 | 200, id=1, fields not null | ✅ Pass |
| testGetAllUsers | GET /users | 200, list size > 0 | ✅ Pass |
| testUserNotFound | GET /users/999 | 404 | ✅ Pass |
| testResponseTime | GET /users/1 | 200, time < 3000ms | ✅ Pass |

### POST Tests
| Test | Endpoint | Assertion | Status |
|------|----------|-----------|--------|
| testCreateUser | POST /users | 201, fields match request | ✅ Pass |
| testCaptureCreatedUserId | POST /users | 201, id > 0 | ✅ Pass |
| testResponseHeaders | POST /users | 201, Content-Type JSON | ✅ Pass |

### PUT Tests
| Test | Endpoint | Assertion | Status |
|------|----------|-----------|--------|
| testUpdateUser | PUT /users/1 | 200, fields updated | ✅ Pass |
| testPartialUpdate | PUT /users/1 | 200, name changed | ✅ Pass |
| testUpdateNonExistentUser | PUT /users/999 | 500 | ✅ Pass |

### DELETE Tests
| Test | Endpoint | Assertion | Status |
|------|----------|-----------|--------|
| testDeleteUser | DELETE /users/1 | 200 | ✅ Pass |
| testDeleteResponseBodyEmpty | DELETE /users/1 | body empty | ✅ Pass |
| testDeleteResponseTime | DELETE /users/1 | time < 3000ms | ✅ Pass |

## RestAssured Pattern
```java
given()                          // setup — headers, auth, body
    .header("Content-Type", "application/json")
    .body(requestBody)
.when()                          // action — HTTP method + endpoint
    .post("/users")
.then()                          // assertion — status, body, headers
    .statusCode(201)
    .body("name", equalTo("Hardik Shah"));
```

## CI/CD
GitHub Actions pipeline runs on every push to main.
Tests execute automatically on Ubuntu with Java 17.