# Code checkpoints and concept coverage

This file is a compact map of concepts to actual implementation. Use RUNBOOK.md for the full step-by-step practical.

## Day 11 — Spring fundamentals
- IoC/DI and component scanning: Main.java, EmployeeController, EmployeeService, repositories.
- Explicit beans, scopes, primary/qualifier, conditional beans: config/AppConfig.java and ClockConsumer.java.
- Lifecycle: config/LifecycleProbe.java.
- AOP and Spring proxy: aspect/ServiceLoggingAspect.java. Observe logs by calling a service through a Spring bean.

## Day 12 — Boot configuration
- Starters and auto-configuration: pom.xml.
- Profiles and externalized values: resources/application*.properties.
- Typed config: config/AppProperties.java.
- Conditional bean creation: config/AppConfig.java.
- Custom auto-configuration: config/EmployeeFeatureAutoConfiguration.java, registered through resources/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports.
- Actuator: /actuator/health.

## Day 13 — REST
- Versioned routes, methods, DTOs, Jackson, validation and status codes: controller/* and dto/ApiDtos.java.
- Global errors: exception/ApiExceptionHandler.java.
- Pagination, filtering and sorting: EmployeeController + EmployeeService + EmployeeSpecifications.
- OpenAPI/Swagger: springdoc dependency and /swagger-ui/index.html.
- Idempotency: service/IdempotencyKeyRegistry.java and EmployeeController create endpoint. Send the same Idempotency-Key with the same JSON twice; the second request reuses the first result. Reusing the key with a different payload returns 400.
- Important limitation: the registry is in-memory and process-local for teaching. Production idempotency needs durable storage, expiry, payload conflict handling and coordination across application instances.

## Day 14 — Persistence
- JDBC/JPA/Hibernate boundary: repositories and entities.
- Relationships and lazy loading: domain/Department.java, Employee.java, Attendance.java.
- Cascade PERSIST only: Department.addEmployee shows the parent-to-child mapping; REMOVE is deliberately not cascaded. The employee creation endpoint explicitly saves through EmployeeRepository.
- JPQL, native SQL and pessimistic lock: repository/EmployeeRepository.java.
- Dynamic query composition: EmployeeSpecifications.java.
- Transactions, propagation and isolation: service/EmployeeService.java and AuditService.java.
- Optimistic lock: Employee.version; pessimistic lock: findByIdForUpdate.
- N+1 mitigation: EntityGraph on paged employee lookup.
- Pooling: Hikari settings in application.properties.
- Migrations: resources/db/migration/V1__initial_schema.sql.

## Day 15 — Security and tests
- Password hashing, filter chain, JWT and role authorization: security/* and AuthController.
- CORS and stateless bearer-token configuration: SecurityConfig.
- Unit test: EmployeeServiceUnitTest and IdempotencyKeyRegistryTest.
- MVC/security integration test: SecurityFlowTest using MockMvc and SpringBootTest.
- Testcontainers PostgreSQL dependencies are present as a next-step introduction; no Testcontainers-backed test is claimed as implemented yet.
- JaCoCo report is generated during mvn verify at target/site/jacoco/index.html.

## Important engineering boundaries
- Demo users and fallback JWT key are local-only; configure a strong JWT_SECRET and persistent identity provider for production.
- JWT is signed, not encrypted.
- Idempotency cache is local memory only.
- EntityGraph solves the demonstrated employee→department fetch pattern, not every possible N+1.
- Tests should be run locally before presenting a successful build claim.