# Week 3: End-to-End Employee Management Application

A runnable Spring Boot application connecting the concepts from Days 11–15 in one employee-management workflow.

## Concept-to-implementation map

| Concept | Implementation | Responsibility |
|---|---|---|
| IoC and Dependency Injection | config/, controller/, service/, repository/ | Spring creates objects and injects dependencies |
| Bean scopes and lifecycle | config/TraceToken.java, LifecycleProbe.java | Demonstrate singleton/prototype scope and bean lifecycle callbacks |
| Java configuration and qualifiers | config/AppConfig.java, ClockConsumer.java | Register beans and select the required implementation |
| Conditional and custom auto-configuration | config/EmployeeFeatureAutoConfiguration.java | Register optional functionality based on configuration |
| AOP and proxies | aspect/ServiceLoggingAspect.java | Log service method execution around a Spring proxy |
| Profiles and external configuration | resources/application*.properties, config/AppProperties.java | Switch local/dev/prod settings without changing Java code |
| REST and DTOs | controller/, dto/ApiDtos.java | Expose versioned HTTP endpoints without returning entities |
| Validation and error handling | dto/, exception/ApiExceptionHandler.java | Reject invalid input and return consistent API errors |
| JPA relationships and queries | domain/, repository/ | Map Employee, Department and Attendance; query persistent data |
| Transactions and locking | service/, repository/EmployeeRepository.java | Define transaction boundaries and demonstrate optimistic/pessimistic locking |
| N+1 query mitigation | repository/EmployeeRepository.java | Fetch the department with the employee query using EntityGraph |
| Database migrations and pooling | resources/db/migration/, application.properties | Manage schema changes with Flyway and connections with HikariCP |
| JWT and authorization | security/, controller/AuthController.java | Authenticate requests and enforce role-based access |
| Automated testing | src/test/java/ | Test service behavior, idempotency and the security request flow |

## End-to-end flow

1. Spring Boot starts and creates the application context.
2. Configuration classes register application beans; component scanning discovers controllers, services and repositories.
3. A client logs in through AuthController and receives a signed JWT.
4. JwtFilter validates the bearer token and Spring Security checks the user's role.
5. EmployeeController receives an HTTP request and validates its DTO.
6. EmployeeService applies business rules and runs inside a transaction.
7. EmployeeRepository uses Spring Data JPA/Hibernate to query or update the database.
8. The response is mapped to a DTO and returned with the appropriate HTTP status.
9. The AOP aspect logs eligible service method execution.
10. Tests verify important behavior without requiring every check to be performed manually.

## Local demo rules

- The default local profile uses an in-memory H2 database.
- Demo users: admin / admin123 and reader / reader123.
- Admin can create, update and delete employees; reader is limited to permitted read operations.
- Employee email must be unique.
- Employee salary must satisfy the request validation rules.
- An employee must reference an existing department.
- Employee listing supports filtering, pagination and an allow-listed sort field.
- Repeating an Idempotency-Key with the same request reuses the stored response in this process; using the same key with a different request is rejected.
- These are learning/demo rules, not a production identity or persistence setup.

## Run

Use JDK 21 and Maven. Open this folder as a Maven project, then run:

```bash
mvn clean test
mvn spring-boot:run
```

Base URL: `http://localhost:8080`

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- Health: `http://localhost:8080/actuator/health`
- H2 console: `http://localhost:8080/h2-console`

The local profile is selected by default. For PostgreSQL, configure `DB_URL`, `DB_USERNAME` and `DB_PASSWORD`, then activate the dev profile. Production additionally requires a strong `JWT_SECRET`. Never use the demo credentials or fallback JWT key in production.

## Hands-on experiments

- Change the ClockConsumer qualifier and observe which Clock bean Spring injects.
- Change the active profile and compare the datasource/configuration selected at startup.
- Turn the attendance feature property off and observe the conditional configuration behavior.
- Call the employee API with valid and invalid DTOs; compare successful responses with validation errors.
- Create multiple employees and use pagination, filtering and sorting.
- Send the same Idempotency-Key and payload twice; verify the second request does not create a second employee. Reuse the key with a different payload and observe the rejection.
- Compare SQL logs for employee listing and explain how the EntityGraph fetch plan avoids the demonstrated department N+1 query.
- Trigger a stale update in two transactions and explain optimistic locking through the entity version.
- Use the salary update method to inspect the pessimistic write-lock query.
- Log in as reader and admin; compare the authorization result for a write endpoint.
- Run the tests, intentionally break one expected behavior, observe the failure, then restore the implementation.

## Design notes

- This is one integrated application, not five disconnected Day 11–15 demos. The same Employee, Department and Attendance domain is used throughout.
- Packages are organized by application responsibility, following the same one-project / subpackages / Main.java structure as Day 9–10's `payroll` project.
- Controllers handle HTTP concerns; services hold business rules and transaction boundaries; repositories handle persistence.
- DTOs are API contracts. JPA entities are not returned directly from controllers.
- JPQL refers to entity and property names; native SQL refers to database table and column names.
- EntityGraph addresses the employee-to-department fetch pattern shown here; it is not a universal fix for every N+1 query.
- JWT is signed, not encrypted. Do not place secrets in its payload.
- The demo idempotency registry is in-memory and process-local. Production idempotency requires durable storage, expiry and coordination across application instances.
- Testcontainers dependencies are included for a future real-PostgreSQL integration-test exercise; the current tests do not claim to run a Testcontainers database.
- JaCoCo creates a coverage report during `mvn verify` at `target/site/jacoco/index.html`.

## Learning sequence

Use `RUNBOOK.md` for the file-by-file Day 11–15 execution sequence and `CODE_CHECKPOINTS.md` to locate each concept in the source code. For every concept, run it, inspect the behavior, explain the code in your own words and complete the hands-on experiment before moving on.
