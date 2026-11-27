# Day 15 — Spring Security and Application Testing

## Security
- Authentication proves identity; authorization decides permissions.
- SecurityFilterChain applies stateless request rules; JWT filter extracts Bearer token.
- BCryptPasswordEncoder hashes passwords; never store plaintext.
- JWT is signed, not encrypted; validate signature and expiry. Avoid sensitive claims.
- Role-based access: ADMIN writes, authenticated USER reads; @EnableMethodSecurity + @PreAuthorize.
- CORS controls browser origins/methods/headers. CSRF is disabled only because this API uses stateless bearer tokens, not cookie sessions.
- Secure API: protect all routes except login/docs/health; production needs persistent users, key rotation, revocation/refresh, rate limits and secrets manager.

## Testing
- JUnit 5 assertions/lifecycle; Mockito mocks collaborators for isolated unit tests.
- MockMvc tests routing, JSON, validation and security without a live HTTP server.
- @SpringBootTest verifies application wiring; unit and integration tests answer different questions.
- Testcontainers runs disposable real PostgreSQL when Docker is available; H2 is fast but not identical to PostgreSQL.
- Use deterministic fixtures and isolate test data; do not rely on test order.
- Test transactional commit/rollback/locking at service boundaries. Test-managed transactions can hide commit-time failures.
- Coverage is a diagnostic, not a goal by itself.

Practical: verify anonymous request → 401, USER on ADMIN route → 403, ADMIN write → success; run mvn test.