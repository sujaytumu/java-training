# Week 3 practical runbook — Employee Management API

This is a code-first guide. For every checkpoint: run it, observe it, explain the request flow, then complete the small exercise. Do not memorize annotations without understanding the object or request they affect.

## Project rule

There is ONE Spring Boot application and ONE Employee/Department/Attendance domain. Days 11–15 are five learning checkpoints over the same codebase. Do not create duplicate applications or duplicate entity classes.

## Before starting

- JDK 21, IntelliJ IDEA, Maven and Postman.
- Open this folder as a Maven project: src/main/java/week_3/pom.xml.
- Wait for Maven sync. Run mvn clean test, then mvn spring-boot:run.
- Default profile is local and uses in-memory H2. No PostgreSQL installation is required for the first run.
- Base URL: http://localhost:8080; Swagger UI: /swagger-ui/index.html; health: /actuator/health.

If the first build fails, stop and fix the build before adding concepts. Do not continue on a broken baseline.

---

## Day 11 — Spring container, IoC/DI, beans and AOP

### Files to open in order
1. employee_management/Main.java
2. employee_management/config/AppConfig.java
3. employee_management/config/ClockConsumer.java
4. employee_management/config/TraceToken.java
5. employee_management/config/LifecycleProbe.java
6. employee_management/aspect/ServiceLoggingAspect.java
7. employee_management/controller/EmployeeController.java → service/EmployeeService.java → repository/EmployeeRepository.java

### Run and observe
- Start the app. Find the LifecycleProbe initialization log.
- Call GET /actuator/health.
- After Day 15 login, call an employee endpoint and observe the service execution timing log from the aspect.

### Explain in your own words
- Spring creates and wires objects (IoC); constructor injection supplies dependencies (DI).
- @Configuration + @Bean registers explicit beans; component scanning discovers @Component classes.
- @Primary selects the default Clock; @Qualifier selects the specifically named businessClock.
- TraceToken is prototype-scoped; singleton is the default scope.
- @PostConstruct runs after bean construction and dependency injection; @PreDestroy runs at context shutdown for managed singleton beans.
- Controller → service → repository separates HTTP, business rules and persistence.
- Spring AOP wraps eligible Spring-managed method calls with a proxy. Around advice logs execution time. Self-invocation within the same object generally bypasses proxy advice.

### Your turn
Change ClockConsumer to request the primary Clock, run, then restore @Qualifier("businessClock"). Explain why the selected bean changes.

---

## Day 12 — Boot architecture, profiles and external configuration

### Files
- pom.xml: starter dependencies and auto-configuration inputs.
- resources/application.properties
- resources/application-local.properties
- resources/application-dev.properties
- resources/application-prod.properties
- employee_management/config/AppProperties.java
- employee_management/config/AppConfig.java

### Run and observe
- Start with no profile argument: local is selected by spring.profiles.default.
- Observe H2 datasource and schema validation in logs.
- Run dev only after PostgreSQL exists and DB_URL/DB_USERNAME/DB_PASSWORD are set: mvn spring-boot:run -Dspring-boot.run.profiles=dev.
- Never run prod with the demo secret. Supply a strong base64-encoded JWT_SECRET and real database credentials.

### Explain
- Starters bring related libraries; auto-configuration conditionally configures beans based on classpath and existing beans.
- application.properties contains common defaults; profile files override environment-specific values.
- @ConfigurationProperties binds a group of settings into a typed object.
- @ConditionalOnProperty and @ConditionalOnMissingBean demonstrate conditional bean registration.
- Actuator exposes operational endpoints; only health/info are exposed here.

### Your turn
Change app.company-name in local properties and inspect the bound AppProperties value in the debugger. Do not hardcode environment secrets in Java.

---

## Day 13 — REST API, DTOs, validation and errors

### Files
1. controller/EmployeeController.java
2. dto/ApiDtos.java
3. service/EmployeeService.java
4. exception/ApiExceptionHandler.java
5. pom.xml (Spring MVC, Validation, Jackson and springdoc)

### Run and observe (after login is implemented on Day 15)
1. Login: POST /api/v1/auth/login, JSON {"username":"admin","password":"admin123"}.
2. Create department: POST /api/v1/departments, JSON {"name":"Engineering"}.
3. Create employee: POST /api/v1/employees, JSON {"name":"Asha Rao","email":"asha@example.com","salary":65000.00,"departmentId":1}.
4. List: GET /api/v1/employees?page=0&size=10&sortBy=name&direction=asc.
5. Try invalid email, blank name, negative salary, unknown department and unknown employee ID.
6. Try unsupported sort property; the API should reject it rather than pass arbitrary property names into sorting.
7. Send the same Idempotency-Key header and same JSON twice; the second call should return the original result without creating another employee. Reuse the key with a changed payload and observe the 400 response.

### Explain
- GET reads; POST creates; PUT replaces; DELETE removes.
- DTOs are API contracts; entities are persistence models. Do not expose JPA entities directly.
- Jackson maps JSON to request records and serializes response records.
- @Valid triggers Bean Validation constraints.
- ResponseEntity controls status and headers; create returns 201 and a Location header, delete returns 204.
- @RestControllerAdvice maps known failures into a consistent error body.
- URI prefix /api/v1 is the versioning convention used in this project.
- Springdoc generates OpenAPI documentation from controller mappings and annotations.

### Your turn
Send one valid and two invalid employee payloads. For each, explain which layer rejects the request and why.

---

## Day 14 — JPA, relationships, transactions and query performance

### Files
1. domain/Department.java, domain/Employee.java, domain/Attendance.java
2. repository/EmployeeRepository.java, repository/EmployeeSpecifications.java
3. service/EmployeeService.java, service/AttendanceService.java, service/AuditService.java
4. resources/db/migration/V1__initial_schema.sql
5. resources/application-local.properties

### Run and observe
- Create a department, employee and attendance record using the API.
- Inspect H2 console at /h2-console; JDBC URL jdbc:h2:mem:week3db, user sa, blank password.
- Turn on SQL logging locally. Compare employee list queries with and without the EntityGraph fetch plan.
- Call list endpoint with name and department filters, pagination and sorting.
- Inspect Flyway startup logs and the flyway_schema_history table.

### Explain
- JDBC is the low-level Java database API; JPA is the persistence specification; Hibernate is the JPA provider/ORM.
- Employee → Department is Many-to-One; Department → Employees is One-to-Many; Employee → Attendance is One-to-Many.
- Lazy relationships load when accessed inside an open persistence context; eager loading loads earlier. Lazy is usually a safer default for collections.
- JPQL refers to entity and field names; native SQL refers to database tables and columns.
- Department has CascadeType.PERSIST for its employee collection and an addEmployee helper. The public create-employee endpoint explicitly saves Employee through EmployeeRepository; inspect the mapping and explain when cascade persist is appropriate. REMOVE is intentionally not cascaded.
- Specifications compose optional predicates dynamically.
- @Transactional defines a transaction boundary. Runtime exceptions trigger rollback by default. readOnly=true is an optimization hint, not a security lock.
- REQUIRES_NEW starts an independent audit transaction when invoked through the Spring proxy; AuditService is a separate bean for that reason.
- READ_COMMITTED is the configured isolation example. Actual guarantees depend on the database.
- @Version enables optimistic locking; @Lock(PESSIMISTIC_WRITE) requests a database write lock.
- N+1 means one query loads parent rows and additional queries load related data repeatedly. EntityGraph is the fetch-plan demonstration.
- HikariCP is Spring Boot's default JDBC connection pool.
- Flyway applies versioned SQL migrations; Hibernate validates the schema instead of silently creating it.

### Your turn
Explain the SQL generated for employee listing, then explain why the department fetch plan avoids repeated lazy SELECTs. Do not claim EntityGraph fixes every possible N+1 scenario.

---

## Day 15 — Security and automated testing

### Files
- security/SecurityConfig.java
- security/JwtService.java
- security/JwtFilter.java
- controller/AuthController.java
- controller/EmployeeController.java
- src/test/java/

### Run and observe
- Login as admin: admin / admin123; login as reader: reader / reader123.
- Copy the access token and send Authorization: Bearer <token>.
- Reader can access authenticated reads but should receive 403 for ADMIN-only create/update/delete operations.
- No token should receive 401 for protected endpoints.
- Run mvn test; inspect test names and assertions.

### Explain
- Authentication answers who the caller is; authorization answers what they may do.
- PasswordEncoder stores a BCrypt hash, not the original password.
- SecurityFilterChain defines request security; JwtFilter reads and validates bearer tokens before authorization.
- Roles are enforced at URL and method level.
- CORS controls which browser origins may call the API. CSRF is disabled here because this is a stateless bearer-token API; reconsider this for cookie-based authentication.
- JWT is signed, not encrypted. Never put secrets or sensitive personal data in its payload.
- Unit tests isolate a class with Mockito; MockMvc tests the MVC/security pipeline without a real network server.
- Spring Boot Test loads the application context. Testcontainers can provide disposable real PostgreSQL for integration tests; its dependency is included as an introduction, not a claim that a container test is already written.
- Code coverage measures which code was executed by tests; high coverage alone does not prove correctness. Run mvn verify and open target/site/jacoco/index.html.

### Your turn
Run tests, then deliberately break one expected status code or service rule and confirm the relevant test fails. Restore the implementation and rerun.

---

## Manager demonstration order (10–15 minutes)

1. Show package structure and explain Controller → Service → Repository.
2. Start the application and show profile, Flyway and Hikari startup logs.
3. Show Swagger and create a Department and Employee.
4. Show a validation error and standard error response.
5. Show pagination, filtering and sorting.
6. Login as reader and demonstrate role restriction; repeat as admin.
7. Explain entity relationships, transaction boundary, locking and N+1 fetch plan using actual files.
8. Run mvn clean test and show passing tests.

## Completion checklist

- [ ] I can explain every class I open without reading a prepared paragraph.
- [ ] I can trace one HTTP request from controller to database and back.
- [ ] I can explain why each annotation is present.
- [ ] I can reproduce valid, invalid, unauthorized and forbidden API calls.
- [ ] I can explain the SQL and transaction behavior I demonstrate.
- [ ] I can run tests and explain what each assertion proves.

If you cannot explain a file yet, mark it "study next" rather than pretending to understand it.