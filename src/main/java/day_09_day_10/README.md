# Day 9–10: End-to-End Payroll Application

A runnable Java 21 application connecting six patterns in one business workflow.

| Pattern | Implementation | Responsibility |
|---|---|---|
| Builder | model/Employee.java | Build a valid immutable employee |
| Chain of Responsibility | validation/ | Validate employee through ordered handlers |
| Strategy | strategy/ | Encapsulate salary calculation algorithms |
| Simple Factory | factory/SalaryStrategyFactory.java | Select the strategy for employee type |
| Abstract Factory | abstractfactory/ | Create a matching tax-calculator and payslip-formatter family |
| Observer | observer/ | Notify independent listeners after saving payroll |

Repository and service classes support the workflow; they are not counted as additional patterns.

## Flow
1. Main builds an Employee.
2. PayrollService runs the validation chain.
3. SalaryStrategyFactory selects a salary strategy.
4. Strategy calculates gross salary.
5. Abstract Factory supplies the tax calculator and payslip formatter.
6. Service calculates tax/net, creates Payroll, and saves it.
7. Observer listeners receive the event after persistence.

## Demo rules (not real payroll/tax advice)
- Full-time gross = base salary + 10,000
- Contract gross = base salary × 1.20
- Intern gross = fixed 15,000
- Standard demo tax = 10%; alternate demo tax = 15%

## Run from repository root
```bash
mvn -DskipTests compile
java -cp target/classes day_09_day_10.payroll.Main
```

## Hands-on experiments
- Change EmployeeType in Main and watch the Factory select a different Strategy.
- Set active(false) and confirm validation stops processing before persistence.
- Replace StandardPayrollComponentFactory with AlternatePayrollComponentFactory; tax calculation and payslip format change together.
- Add a PayrollObserver implementation and register it in Main without changing PayrollService.

## Design notes
- BigDecimal is used for money to avoid binary floating-point rounding issues.
- Employee is immutable; validation happens at Builder.build().
- Every validator performs one check and delegates to the next handler.
- Simple Factory chooses one algorithm; Abstract Factory creates a family of related components.
- Observer notifications happen after saving. This demo logs listener failures and continues; production systems may require an outbox and retries.
- The repository is in-memory; records disappear when the application exits.
