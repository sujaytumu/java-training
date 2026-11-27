# Week 3 — Employee Management REST API

A single Spring Boot application covering Days 11–15. The same domain and application are extended in five practical learning stages.

## Structure
- employee_management/: Java source grouped by responsibility (config, controller, domain, dto, exception, repository, security, service, aspect) and Main.java.
- resources/: profile configuration and Flyway SQL migration.
- pom.xml: standalone Maven build, test dependencies and JaCoCo.
- RUNBOOK.md: file-by-file learning sequence, run commands, API exercises and manager demo.
- CODE_CHECKPOINTS.md: quick map from each syllabus concept to its actual implementation.

## Run
Use JDK 21 and Maven. From this directory run mvn clean test, then mvn spring-boot:run. The default local profile uses in-memory H2. Swagger UI: http://localhost:8080/swagger-ui/index.html. Health: http://localhost:8080/actuator/health.

Demo login: POST /api/v1/auth/login with JSON {"username":"admin","password":"admin123"}; use the returned Bearer token. Reader login is reader / reader123. These in-memory accounts and fallback JWT key are local learning conveniences only.

## Profiles
- local: H2.
- dev: PostgreSQL using DB_URL, DB_USERNAME and DB_PASSWORD.
- prod: PostgreSQL and JWT_SECRET supplied through environment variables.

## API
| Method | Endpoint | Purpose |
|---|---|---|
| POST | /api/v1/auth/login | Issue JWT |
| POST/GET | /api/v1/departments | Create/list departments |
| POST/GET/PUT/DELETE | /api/v1/employees | Employee CRUD (detail uses /{id}) |
| GET | /api/v1/employees?page=0&size=10&sortBy=name | Pagination, filtering and sorting |
| POST | /api/v1/attendance | Record attendance |
| GET | /api/v1/attendance/employee/{id} | Attendance history |

## Concepts
IoC/DI, bean scopes and lifecycle, component scanning, @Bean/@Configuration, @Primary/@Qualifier, conditional beans, AOP proxy, profiles, ConfigurationProperties, conditional and custom auto-configuration, REST DTOs, Jackson, Bean Validation, global errors, HTTP status codes, URI versioning, idempotency fundamentals, JPA relationships and cascade, lazy loading, JPQL, native SQL, Specifications, transactions, propagation, isolation, optimistic/pessimistic locking, N+1 fetch planning, HikariCP, Flyway, JWT, BCrypt, role-based authorization, CORS, JUnit, Mockito, MockMvc, Testcontainers introduction and code coverage.

See RUNBOOK.md and CODE_CHECKPOINTS.md for the exact practical sequence.