# Day 14 — Spring Data JPA and Database Integration

- JDBC is the low-level Connection/PreparedStatement/ResultSet API. JPA is a persistence specification; Hibernate is the ORM implementation.
- Entity states: transient → managed → detached → removed. Dirty checking tracks managed changes; SQL may be delayed until flush/commit (write-behind).
- Relationships: Department One-to-Many Employees; Employee Many-to-One Department and One-to-Many Attendance; Attendance Many-to-One Employee.
- Lazy/eager: avoid loading full object graphs unnecessarily; explicit LAZY plus entity graph/fetch query controls loading.
- Cascading propagates lifecycle operations; avoid broad REMOVE when department deletion must not delete all employees.
- JPQL queries entity model; native SQL queries database tables. Specifications compose optional filters dynamically.
- Put @Transactional at service boundary. Runtime exceptions roll back by default.
- Propagation: REQUIRES_NEW audit transaction commits independently of outer transaction; understand audit can survive outer rollback.
- Isolation: READ_COMMITTED prevents dirty reads; exact behavior depends on database.
- Optimistic locking: @Version detects concurrent updates. Pessimistic WRITE lock blocks competing writers while transaction is open.
- N+1: lazy relation access in a loop can issue one query per row; use entity graphs, fetch joins, projections or batch fetching.
- HikariCP is Boot's default pool; tune pool size/timeouts against database capacity.
- Flyway V1 migration versions schema; Hibernate validates rather than mutating production schema.

Practical: create department, employees and attendance; filter/paginate; inspect SQL; compare fetch plan; test concurrent update.