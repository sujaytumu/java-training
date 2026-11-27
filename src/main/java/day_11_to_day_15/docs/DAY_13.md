# Day 13 — REST API Development

- HTTP request/response structure: method, path, headers, body; status, headers, representation.
- REST: resource-oriented paths, stateless interactions, uniform HTTP semantics and representations.
- Methods: GET read, POST create, PUT replace, PATCH partial update, DELETE remove. Statuses: 200, 201 + Location, 204, 400, 401, 403, 404, 409, 500.
- Request/response DTOs isolate public API contracts from persistence entities.
- Jackson handles JSON serialization/deserialization through Spring MVC message converters.
- Bean Validation: @Valid with @NotBlank, @Email, @Positive, @NotNull, @Size and @Digits.
- @RestControllerAdvice centralizes error mapping; ResponseEntity controls body/status/headers.
- Pageable/Page implement bounded pagination and sorting; allowlist sort properties and cap page size.
- URI versioning uses /api/v1; springdoc exposes OpenAPI/Swagger.
- Idempotency: GET/PUT/DELETE are intended idempotent; POST generally is not. Retry-safe POST needs persisted key, payload matching, concurrency control and atomic result recording; a header alone is not enough.

Practical: CRUD employee API, invalid DTO, missing ID, pagination/sorting and inspect Swagger.