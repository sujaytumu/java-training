# Week 3 — Spring Framework and Spring Boot

A separate Maven/Spring Boot Employee Management API for Days 11–15. Existing Days 1–10 and earlier Day 11–13 folders are untouched.

**Stack:** Java 21, Spring Boot 3.5.7, Spring MVC, Spring Data JPA/Hibernate, H2/PostgreSQL, Flyway, Validation, Spring Security/JWT, AOP, Actuator, OpenAPI, JUnit 5, Mockito, MockMvc and Testcontainers dependencies.

## Run
From this directory: `mvn spring-boot:run`. Default profile is local H2. Swagger: http://localhost:8080/swagger-ui/index.html ; health: http://localhost:8080/actuator/health ; H2 console: http://localhost:8080/h2-console (JDBC `jdbc:h2:mem:week3db`, user `sa`, blank password). Run tests with `mvn test`.

Demo login: POST `/api/v1/auth/login` with `{"username":"admin","password":"admin123"}`; use returned Bearer token. Demo in-memory users are for learning only, not production.

## Profiles
`local` uses H2; `dev` and `prod` use PostgreSQL via DB_URL, DB_USERNAME, DB_PASSWORD. Production JWT key must be supplied as JWT_SECRET. Activate dev with `mvn spring-boot:run -Dspring-boot.run.profiles=dev`. Flyway owns schema migrations; Hibernate validates schema.

## API
| Method | Endpoint | Access / purpose |
|---|---|---|
| POST | /api/v1/auth/login | Public; issue JWT |
| POST | /api/v1/departments | ADMIN; create department |
| GET | /api/v1/departments | Authenticated; list |
| POST | /api/v1/employees | ADMIN; create employee |
| GET | /api/v1/employees/{id} | Authenticated; details |
| GET | /api/v1/employees?page=0&size=10&sortBy=name | Authenticated; pagination/filter/sort |
| PUT/DELETE | /api/v1/employees/{id} | ADMIN; update/delete |
| POST | /api/v1/attendance | ADMIN; record attendance |
| GET | /api/v1/attendance/employee/{id} | Authenticated; history |

## Design notes
Controller → service → repository. DTOs are separate from entities. Employee–Department is Many-to-One/One-to-Many; Employee–Attendance is One-to-Many/Many-to-One. Lazy relationships, optimistic @Version, pessimistic locking query, Specifications, service transactions, REQUIRES_NEW audit propagation, READ_COMMITTED isolation, fetch plan to mitigate N+1, HikariCP and Flyway are demonstrated. JWT is signed (not encrypted), short-lived and stateless. CORS is explicit; CSRF is disabled for bearer-token API only. Production requires persistent identity, key rotation, token revocation/refresh, secret management, rate limiting and security review.

Read docs/DAY_11.md through DAY_15.md in order.