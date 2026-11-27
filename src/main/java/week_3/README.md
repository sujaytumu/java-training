# Week 3 — Employee Management REST API

A single Spring Boot application covering the Week 3 learning outcomes (Days 11–15).

## Structure
- `employee_management/`: Java source, organized by responsibility (config, controller, domain, dto, exception, repository, security, service, aspect) and `Main.java`.
- `resources/`: application profiles and Flyway database migration.
- `pom.xml`: standalone Maven build.

## Run
Use Java 21 and Maven. From this directory run `mvn spring-boot:run`. The default local profile uses an in-memory H2 database. Swagger UI: http://localhost:8080/swagger-ui/index.html. Health: http://localhost:8080/actuator/health.

Demo login: POST `/api/v1/auth/login` with `{"username":"admin","password":"admin123"}`; use the returned Bearer token. Demo in-memory accounts and fallback JWT key are for local learning only.

## Profiles
- `local`: H2.
- `dev`: PostgreSQL using DB_URL, DB_USERNAME and DB_PASSWORD.
- `prod`: PostgreSQL and JWT_SECRET supplied through environment variables.

## API
| Method | Endpoint | Purpose |
|---|---|---|
| POST | /api/v1/auth/login | Issue JWT |
| POST/GET | /api/v1/departments | Create/list departments |
| POST/GET/PUT/DELETE | /api/v1/employees | Employee CRUD (GET detail uses /{id}) |
| GET | /api/v1/employees?page=0&size=10&sortBy=name | Pagination, filtering and sorting |
| POST | /api/v1/attendance | Record attendance |
| GET | /api/v1/attendance/employee/{id} | Attendance history |

The implementation demonstrates IoC/DI, bean scopes and lifecycle, AOP, profiles, conditional configuration, REST DTOs and validation, global errors, JPA relationships, Specifications, transactions and locking, N+1 mitigation, Flyway, JWT, role-based access and CORS.

Run `mvn package` to build.