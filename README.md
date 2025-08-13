# News Aggregator API

A Spring Boot 3.4+ REST API for user authentication, profile management, news preference handling, and fetching personalized news from the GNews API.

---

## 📌 Features
- **Authentication & Registration** with JWT
- **User Profile Management**
- **News Preferences** CRUD
- **Fetch Personalized News** from GNews API
- **Centralized Exception Handling**
- **Unit Tests** for all service layers and Repository classes with custom methods

---

## 🛠 Tech Stack
- Java 17+
- Spring Boot 3.4+
- Spring Security (JWT)
- Spring Data JPA (H2)
- Lombok
- Logback for logging
- JUnit 5 & Mockito for testing

---

## 📂 Project Structure
```plaintext
com.lvrgese.news_aggregator
├── auth/          # Auth-related controllers & services
├── controller/    # REST controllers
├── dto/           # Data Transfer Objects
├── entity/        # JPA Entities
├── exception/     # Custom Exceptions
├── repository/    # Spring Data Repositories
├── service/       # Business Logic Services
└── util/          # Utility Classes (e.g., JWT)


```
---

## 🧪 Testing Overview

### ✅ Unit Tests
- **Services**:
  - `AuthServiceTest` – covers register & login (happy/error paths)
  - `UserServiceTest` – covers retrieving current user & profile
  - `NewsPreferencesServiceTest` – covers CRUD operations on preferences
  - `NewsServiceTest` – covers:
    1. Fetch news successfully
    2. No preferences set (`ResourceNotFoundException`)
    3. GNews API failure (`GNewsFetchException`)
    4. `sortBy` validation fallback
- **Repositories**:
  - Example `UserRepositoryTest` using `@DataJpaTest`


### 🚀 Coverage
Unit tests cover all main business logic paths.  
`NewsService` is fully tested for both success and error scenarios, including `sortBy` fallback validation.

---

## ⚙️ Running Tests

mvn clean test

## 📜 API Endpoints

| Method | Endpoint              | Description                       |
|--------|-----------------------|-----------------------------------|
| POST   | `/api/register`       | Register new user                 |
| POST   | `/api/login`          | Login user and get JWT            |
| GET    | `/api/profile`        | Get current user profile          |
| GET    | `/api/preferences`    | Get user news preferences         |
| POST   | `/api/preferences`    | Create news preferences           |
| PUT    | `/api/preferences`    | Update news preferences           |
| GET    | `/api/news`           | Fetch personalized news           |

---

## 🔐 Security
- JWT-based authentication  
- Secured endpoints require a valid token in:  Authorization: Bearer <token>


---

## 📝 Logging
- Uses Lombok's `@Slf4j` in all services and controllers
- **Info** logs for successful actions
- **Warn** logs for invalid operations
- **Error** logs for exceptions

---

## 🗄 Configuration
Example `application.properties`:
```properties
spring.application.name=NewsAggregatorAPI
gnews.api.key = YOUR_API_KEY

spring.h2.console.enabled=true
spring.h2.console.path=/h2
spring.datasource.url=jdbc:h2:mem:news_api_db
spring.datasource.username=root
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

```
# 📌 Example Requests & Responses

## 1️⃣ Register User
**Request**  
POST /api/register  
Content-Type: application/json

```json
{
  "username": "john_doe",
  "name": "John Doe",
  "password": "password123"
}
```

**Response**
```json
{
  "userId": 1,
  "name": "John Doe",
  "username": "john_doe",
  "token": "eyJhbGciOiJIUzI1..."
}
```

---

## 2️⃣ Login User
**Request**  
POST /api/login  
Content-Type: application/json

```json
{
  "username": "john_doe",
  "password": "password123"
}
```

**Response**
```json
{
  "userId": 1,
  "name": "John Doe",
  "username": "john_doe",
  "token": "eyJhbGciOiJIUzI1..."
}
```

---

## 3️⃣ Get Current User Profile
**Request**  
GET /api/profile  
Authorization: Bearer eyJhbGciOiJIUzI1...

_No request body_

**Response**
```json
{
  "userId": 1,
  "username": "john_doe",
  "name": "John Doe"
}
```

---

## 4️⃣ Get News Preferences
**Request**  
GET /api/preferences  
Authorization: Bearer eyJhbGciOiJIUzI1...

_No request body_

**Response**
```json
{
  "prefId": 1,
  "query": "technology",
  "lang": "en",
  "country": "us",
  "count": 5,
  "sortBy": "publishedAt"
}
```

---

## 5️⃣ Create News Preferences
**Request**  
POST /api/preferences  
Authorization: Bearer eyJhbGciOiJIUzI1...  
Content-Type: application/json

```json
{
  "query": "technology",
  "lang": "en",
  "country": "us",
  "count": 5,
  "sortBy": "publishedAt"
}
```

**Response**
```json
{
  "prefId": 1,
  "query": "technology",
  "lang": "en",
  "country": "us",
  "count": 5,
  "sortBy": "publishedAt"
}
```

---

## 6️⃣ Update News Preferences
**Request**  
PUT /api/preferences  
Authorization: Bearer eyJhbGciOiJIUzI1...  
Content-Type: application/json

```json
{
  "query": "sports",
  "lang": "en",
  "country": "in",
  "count": 10,
  "sortBy": "relevance"
}
```

**Response**
```json
{
  "prefId": 1,
  "query": "sports",
  "lang": "en",
  "country": "in",
  "count": 10,
  "sortBy": "relevance"
}
```

---

## 7️⃣ Fetch Personalized News
**Request**  
GET /api/news  
Authorization: Bearer eyJhbGciOiJIUzI1...

_No request body_

**Response**
```json
{
  "totalArticles": 10,
  "articles": [
    {
      "title": "AI breakthrough in 2025",
      "description": "New AI model sets performance record",
      "url": "https://example.com/article",
      "publishedAt": "2025-08-13T10:00:00Z"
    }
  ]
}
```
