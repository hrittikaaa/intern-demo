# Intern Demo - Spring Boot User Management Application

This is a full-stack Spring Boot application that provides a RESTful API for managing users, connected to a PostgreSQL database, with a custom modern dark-theme frontend.

## Features
- **Full CRUD operations**: Create, Read, Update, Delete users.
- **PostgreSQL Integration**: Data is persisted seamlessly using Spring Data JPA.
- **Backend Validation**: Includes native validation for mandatory fields and unique email checks.
- **Pagination**: The users list is fully paginated natively on the backend via Spring's `Pageable`.
- **Custom UI**: A sleek, dark-themed frontend with toast notifications and direct API endpoint testing.

---

## 📸 Screenshots

### 1. Browser showing `/hello` working

![Browser showing /hello working](screenshots/helloWorldEndpoint.png)
*Figure 1: The `/hello` endpoint successfully returning a response.*


### 2. DB connection success (Console log or DB Client)

![DB connection success](screenshots/UsersEndpoint.png)
*Figure 2: The UI successfully fetching and displaying data from the PostgreSQL database.*

---

## Prerequisites
- Java 17 or higher
- Maven
- PostgreSQL installed and running locally

## Configuration
Before running the application, ensure you have a PostgreSQL database named `intern_demo` and update the `src/main/resources/application.properties` with your credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/intern_demo
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

## Running the Application
To run the application locally, use the Maven wrapper:
```bash
./mvnw spring-boot:run
```

Once started, access the UI at:
**http://localhost:8080**
