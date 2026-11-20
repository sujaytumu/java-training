# Day 09 + Day 10 — SOLID and Design Patterns
Run `day_09_day_10.Main` using Java 21 from IntelliJ or Maven.

## Practical
Start with `bad/BadPayrollExample.java` to see the anti-pattern: validation, salary rules and notification mixed in one class. Compare with `service/PayrollService.java`.

## Implemented
- **SRP / SoC:** calculation, validation, persistence, and notification separated.
- **OCP / LSP:** salary strategies share a contract; extend with new implementations.
- **ISP / DIP / DI:** small interfaces, constructor-injected repository, specification, validator and observers.
- **Factory:** `SalaryStrategyFactory` selects strategy based on employee type.
- **Builder:** `Employee.Builder` makes object construction readable.
- **Strategy:** full-time, intern and contract gross salary algorithms.
- **Specification:** eligibility rule, with reusable `and` combinator.
- **Chain of Responsibility:** active and salary validation handlers.
- **Observer:** simulated email and employee notification after save.
- **Repository:** in-memory persistence adapter, replaceable by a DB implementation.
- **Command:** payroll action represented as an executable object.
- **Template Method:** fixed processing sequence, customizable gross calculation.
- **State (simplified):** enum-based lifecycle guard. A full GoF State pattern would use separate state classes.

## Architecture notes
- **Composition over inheritance:** PayrollService composes collaborators rather than inheriting from them.
- **DDD:** Employee is an entity; Payroll is a record representing a payroll result. Real money should use BigDecimal, not double.
- **Layered:** business service and repository are separate; a REST controller can be added later.
- **Hexagonal:** PayrollRepository is an outbound port; InMemoryPayrollRepository is an adapter.
- **Scope:** no real database, email sending, payroll regulations or Spring Boot integration. Tax is a simplified fixed 10% teaching example.

## Expected main output
```
Payroll saved for employee ID: 101
Payslip email simulated for Sujay
Notification generated for Sujay
Gross Salary: 40000.0
Tax: 4000.0
Net Salary: 36000.0
Payroll saved for employee ID: 102
Payslip email simulated for Intern
Notification generated for Intern
Workflow status: COMPLETED
```
