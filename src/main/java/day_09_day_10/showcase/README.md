# Day 9 & 10 — Integrated Design Pattern Showcase

This is an additional, self-contained practical. It does not replace or modify the existing `day_09_day_10` implementation.

## Six patterns demonstrated in one payroll flow

1. **Builder — Employee creation:** builds an immutable Employee and validates required fields when `build()` is called.
2. **Chain of Responsibility — Validation:** checks employee activity and payroll eligibility in separate handlers. A failed check stops the chain.
3. **Factory — Algorithm selection:** maps employee type to the correct salary strategy.
4. **Strategy — Salary calculation:** keeps full-time, contract and intern salary formulas in separate implementations.
5. **Observer — Post-save reactions:** after payroll is saved, independent observers simulate a payslip email and an audit event.
6. **Command — User action:** wraps the request in `ProcessPayrollCommand`; the button/Invoker only calls `execute()`.

The repository interface and in-memory implementation support the example; they are not counted as one of the six GoF patterns.

## End-to-end execution

```text
Client builds Employee
  -> PayrollButton (Invoker)
  -> ProcessPayrollCommand.execute()
  -> PayrollService.process(employee)
  -> EmployeeValidator chain
  -> SalaryStrategyFactory selects strategy
  -> SalaryStrategy calculates gross salary
  -> PayrollService calculates tax and net salary
  -> PayrollRepository saves payroll
  -> PayrollObservers receive the saved payroll
```

## Run

Requirements: Java 21 and Maven (the repository's configured Java version).

From the repository root:

```bash
mvn -DskipTests compile
java -cp target/classes day_09_day_10.showcase.PayrollDesignShowcase
```

The class uses only the Java standard library, so no additional dependency is required.

## Expected output

```text
[Invoker] Manager clicked Process Payroll
[Repository] Payroll saved for employee 101
[Observer] Payslip email simulated for Sujay
[Observer] Audit event recorded for employee 101

Saved payroll: Payroll[employeeId=101, gross=40000.00, tax=4000.0000, net=36000.0000]

Rejected as expected: Employee is inactive
```

## What the numbers mean

For the demonstration employee:

- Basic salary: 30,000
- Full-time allowance: 10,000
- Gross salary: 40,000
- Teaching-example tax rate: 10%
- Tax: 4,000
- Net salary: 36,000

The formulas and tax rate are deliberately simplified for learning; they are not payroll or tax advice.

## Useful implementation observations

- Builder setters only collect values; validation of the complete object occurs in `build()`.
- The validation chain checks activity first. If the employee is inactive, salary eligibility and all later payroll work are skipped.
- Factory chooses a Strategy; Strategy performs the calculation. They solve different problems.
- Payroll is saved before observers are called. If an observer fails, the other observers still get a chance to run. This demo logs the failure but does not implement durable retries or an outbox.
- `BigDecimal` is used for money instead of `double` to avoid binary floating-point representation issues.
- The repository is in-memory, and email/audit actions are simulated. This is a runnable design-pattern exercise, not a production payroll system.

## Manager explanation

"I connected six patterns in one workflow rather than implementing them as isolated examples. Builder creates a valid employee, the validation chain checks whether payroll can proceed, Factory selects the salary Strategy, and the service calculates and saves the payroll. Observer handles post-save reactions, while Command represents the manager's request independently of the UI button. I also used BigDecimal for monetary values and demonstrated the inactive-employee failure path to show that validation stops processing before persistence and notifications."
