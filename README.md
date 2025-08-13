# News Aggregator API

A Spring Boot 3.4+ REST API for user authentication, profile management, news preference handling, and fetching personalized news from the GNews API.

---

## 📌 Features
- **Authentication & Registration** with JWT
- **User Profile Management**
- **News Preferences** CRUD
- **Fetch Personalized News** from GNews API
- **Centralized Exception Handling**
- **Unit Tests** for all service layers and controller slices
- **Uses Modern Spring Boot Testing** with `@MockitoBean` (Spring Boot 3.4+ replacement for `@MockBean`)

---

## 🛠 Tech Stack
- Java 17+
- Spring Boot 3.4+
- Spring Security (JWT)
- Spring Data JPA (MySQL)
- Lombok
- Logback for logging
- JUnit 5 & Mockito for testing

---

## 📂 Project Structure
com.lvrgese.news_aggregator
├── auth/ # Auth-related controllers & services
├── controller/ # REST controllers
├── dto/ # Data Transfer Objects
├── entity/ # JPA Entities
├── exception/ # Custom Exceptions
├── repository/ # Spring Data Repositories
├── service/ # Business Logic Services
└── util/ # Utility Classes (e.g., JWT)


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
- **Controllers**:
  - `AuthControllerTest` using `@WebMvcTest` + `@MockitoBean`
    - Covers register/login success
    - Covers register/login error responses

### 🚀 Coverage
Unit tests cover all main business logic paths.  
`NewsService` is fully tested for both success and error scenarios, including `sortBy` fallback validation.

---

## ⚙️ Running Tests

mvn clean test

Method	Endpoint	Description
POST	/api/register	Register new user
POST	/api/login	Login user and get JWT
GET	/api/profile	Get current user profile
GET	/api/preferences	Get user news preferences
POST	/api/preferences	Create news preferences
PUT	/api/preferences	Update news preferences
GET	/api/news	Fetch personalized news

🔐 Security
JWT-based authentication

Secured endpoints require a valid token in Authorization: Bearer <token> header

📝 Logging
Uses Lombok's @Slf4j in all services and controllers

Info logs for successful actions

Warn logs for invalid operations

Error logs for exceptions

🗄 Configuration
application.properties example:

spring.datasource.url=jdbc:mysql://localhost:3306/newsdb
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update

gnews.api.key=your-gnews-api-key
