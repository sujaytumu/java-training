# Day 12 + Day 13 — Spring Boot Configuration and REST API Development

This combined practical extends the earlier Spring training work without modifying the existing `day_11` package.

## Day 12 — Spring Boot architecture and configuration

Topics demonstrated:
- Externalized configuration in `application.properties`.
- Profile-specific configuration: `local`, `dev`, and `prod`.
- `@ConfigurationProperties` for type-safe application settings.
- Conditional bean registration with `@ConditionalOnProperty`.
- Default bean registration with `@ConditionalOnMissingBean`.
- Logging and Actuator health endpoint.
- H2 for local practice and PostgreSQL configuration for dev/prod.

The application defaults to the `local` profile, so it can run without a separately installed database.

Run another profile with:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

For production, set `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` as environment variables and activate the `prod` profile. Production schema management should use controlled migrations rather than automatic schema updates.

### properties vs YAML
Both formats express external configuration. Properties uses dotted key/value pairs; YAML represents the same hierarchy through indentation. Choose one format consistently and avoid defining conflicting values in both.

### Custom auto-configuration concept
The `AppConfiguration` class demonstrates conditional bean creation. A reusable library can package similar configuration behind auto-configuration imports so applications receive sensible defaults that can be overridden by their own beans.

## Day 13 — REST API development

The Employee API demonstrates:
- Resource-oriented endpoints under `/api/v1/employees`.
- GET, POST, PUT and DELETE.
- Request and response DTOs separate from the JPA entity.
- Jackson JSON serialization/deserialization through Spring HTTP message converters.
- Bean Validation with `@Valid`.
- Standard error bodies through `@RestControllerAdvice`.
- `ResponseEntity`, including `201 Created`, `Location`, and `204 No Content`.
- Database-backed pagination and sorting using Spring Data `Pageable`.
- Sort-field allowlisting and a maximum page size.
- OpenAPI/Swagger UI.
- API versioning through the URI.

### Run locally

Requirements: Java 21 and Maven.

```bash
mvn spring-boot:run
```

The local profile uses an in-memory H2 database.

### API endpoints

| Method | Endpoint | Expected success |
|---|---|---|
| POST | `/api/v1/employees` | 201 Created |
| GET | `/api/v1/employees?page=0&size=10` | 200 OK |
| GET | `/api/v1/employees/{id}` | 200 OK |
| PUT | `/api/v1/employees/{id}` | 200 OK |
| DELETE | `/api/v1/employees/{id}` | 204 No Content |

Create an employee:
```bash
curl -i -X POST http://localhost:8080/api/v1/employees \
  -H "Content-Type: application/json" \
  -d '{"name":"Sujay","salary":50000}'
```

Get employees with pagination and sorting:
```bash
curl "http://localhost:8080/api/v1/employees?page=0&size=5&sortBy=salary&direction=desc"
```

Get one employee:
```bash
curl http://localhost:8080/api/v1/employees/1
```

Replace employee details:
```bash
curl -i -X PUT http://localhost:8080/api/v1/employees/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Sujay Updated","salary":60000}'
```

Delete:
```bash
curl -i -X DELETE http://localhost:8080/api/v1/employees/1
```

### Useful URLs
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- Actuator health: `http://localhost:8080/actuator/health`
- H2 console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:employeesdb`, user: `sa`, blank password)

### Validation/error tests

Invalid request:
```json
{
  "name": "   ",
  "salary": -500
}
```
Expected: `400 Bad Request` with field-level errors.

Unknown employee:
```bash
curl -i http://localhost:8080/api/v1/employees/99999
```
Expected: `404 Not Found` with a consistent error response.

Invalid page size or unsupported sort field returns `400 Bad Request`.

## Important concepts and implementation notes

- **PUT vs PATCH:** PUT replaces the editable representation; PATCH applies a partial modification. This practical implements full replacement with PUT.
- **Idempotency:** GET, PUT and DELETE are idempotent by HTTP semantics. POST is generally not. An idempotency-key implementation for retry-safe POST requests requires persistent key storage, payload matching, concurrency control, and atomic recording of the operation result; this is covered conceptually rather than implemented here.
- **Pagination:** `Page` includes total-count metadata; `Slice` can be preferable when a total count is unnecessary.
- **Validation boundary:** DTO constraints validate incoming shape and values. Business rules that depend on current database state still belong in the service layer.
- **Error safety:** Unexpected exception details are not exposed to clients. Log diagnostic details server-side.
- **Database profiles:** H2 is for local learning. PostgreSQL credentials are externalized for dev/prod. Use schema migrations and least-privilege credentials in real deployments.

## Suggested verification
1. Start the app with the default local profile.
2. Create two or more employees.
3. Verify GET by ID and GET all.
4. Verify page 0 and page 1, and descending salary sort.
5. Try blank names, null salaries, negative salaries, invalid page sizes and unsupported sort fields.
6. Request a missing employee ID.
7. Update and delete an employee.
8. Open Swagger UI and inspect the generated OpenAPI document.
