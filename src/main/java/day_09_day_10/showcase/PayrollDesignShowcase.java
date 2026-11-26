package day_09_day_10.showcase;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * One runnable example demonstrating six patterns in a single payroll flow:
 * Builder, Chain of Responsibility, Factory, Strategy, Observer and Command.
 */
public class PayrollDesignShowcase {
    private static final BigDecimal TAX_RATE = new BigDecimal("0.10");

    public static void main(String[] args) {
        // CLIENT: build the employee and wire the application's collaborators.
        Employee employee = new Employee.Builder()
                .id(101)
                .name("Sujay")
                .type(EmployeeType.FULL_TIME)
                .basicSalary(new BigDecimal("30000.00"))
                .active(true)
                .build();

        EmployeeValidator validators = new ActiveEmployeeValidator();
        validators.setNext(new SalaryEligibilityValidator());

        PayrollRepository repository = new InMemoryPayrollRepository();
        List<PayrollObserver> observers = List.of(
                new PayslipEmailObserver(),
                new PayrollAuditObserver()
        );
        PayrollService service = new PayrollService(
                validators, new SalaryStrategyFactory(), repository, observers);

        // COMMAND: the button receives an action object, not payroll business logic.
        Command processPayroll = new ProcessPayrollCommand(service, employee);
        PayrollButton button = new PayrollButton(processPayroll);
        button.press();

        System.out.println("\nSaved payroll: " + repository.findByEmployeeId(101));

        // A negative-path demonstration: inactive employees are rejected
        // before salary calculation, persistence or observer notification.
        Employee inactive = new Employee.Builder()
                .id(102)
                .name("Inactive Employee")
                .type(EmployeeType.CONTRACT)
                .basicSalary(new BigDecimal("20000.00"))
                .active(false)
                .build();
        try {
            new ProcessPayrollCommand(service, inactive).execute();
        } catch (IllegalArgumentException ex) {
            System.out.println("\nRejected as expected: " + ex.getMessage());
        }
    }

    // -------------------- BUILDER --------------------
    enum EmployeeType { FULL_TIME, CONTRACT, INTERN }

    static final class Employee {
        private final int id;
        private final String name;
        private final EmployeeType type;
        private final BigDecimal basicSalary;
        private final boolean active;

        private Employee(Builder builder) {
            this.id = builder.id;
            this.name = Objects.requireNonNull(builder.name, "name is required").trim();
            this.type = Objects.requireNonNull(builder.type, "employee type is required");
            this.basicSalary = Objects.requireNonNull(builder.basicSalary, "basic salary is required");
            this.active = builder.active;

            if (id <= 0 || name.isBlank() || basicSalary.signum() < 0) {
                throw new IllegalArgumentException("Invalid employee details");
            }
        }

        int id() { return id; }
        String name() { return name; }
        EmployeeType type() { return type; }
        BigDecimal basicSalary() { return basicSalary; }
        boolean active() { return active; }

        static final class Builder {
            private int id;
            private String name;
            private EmployeeType type;
            private BigDecimal basicSalary;
            private boolean active = true;

            Builder id(int id) { this.id = id; return this; }
            Builder name(String name) { this.name = name; return this; }
            Builder type(EmployeeType type) { this.type = type; return this; }
            Builder basicSalary(BigDecimal salary) { this.basicSalary = salary; return this; }
            Builder active(boolean active) { this.active = active; return this; }
            Employee build() { return new Employee(this); }
        }
    }

    // -------------------- CHAIN OF RESPONSIBILITY --------------------
    interface EmployeeValidator {
        void validate(Employee employee);
        EmployeeValidator setNext(EmployeeValidator next);
    }

    abstract static class BaseEmployeeValidator implements EmployeeValidator {
        private EmployeeValidator next;

        @Override
        public EmployeeValidator setNext(EmployeeValidator next) {
            this.next = next;
            return next;
        }

        @Override
        public final void validate(Employee employee) {
            check(employee);
            if (next != null) {
                next.validate(employee);
            }
        }

        protected abstract void check(Employee employee);
    }

    static final class ActiveEmployeeValidator extends BaseEmployeeValidator {
        @Override
        protected void check(Employee employee) {
            if (!employee.active()) {
                throw new IllegalArgumentException("Employee is inactive");
            }
        }
    }

    static final class SalaryEligibilityValidator extends BaseEmployeeValidator {
        @Override
        protected void check(Employee employee) {
            // Interns receive a fixed stipend; other employee types need a base salary.
            if (employee.type() != EmployeeType.INTERN
                    && employee.basicSalary().signum() <= 0) {
                throw new IllegalArgumentException("Base salary must be greater than zero");
            }
        }
    }

    // -------------------- STRATEGY --------------------
    interface SalaryStrategy {
        BigDecimal calculateGrossSalary(Employee employee);
    }

    static final class FullTimeSalaryStrategy implements SalaryStrategy {
        @Override
        public BigDecimal calculateGrossSalary(Employee employee) {
            return employee.basicSalary().add(new BigDecimal("10000.00"));
        }
    }

    static final class ContractSalaryStrategy implements SalaryStrategy {
        @Override
        public BigDecimal calculateGrossSalary(Employee employee) {
            return employee.basicSalary().multiply(new BigDecimal("1.20"));
        }
    }

    static final class InternSalaryStrategy implements SalaryStrategy {
        @Override
        public BigDecimal calculateGrossSalary(Employee employee) {
            return new BigDecimal("15000.00");
        }
    }

    // -------------------- FACTORY --------------------
    static final class SalaryStrategyFactory {
        private final Map<EmployeeType, SalaryStrategy> strategies;

        SalaryStrategyFactory() {
            Map<EmployeeType, SalaryStrategy> map = new EnumMap<>(EmployeeType.class);
            map.put(EmployeeType.FULL_TIME, new FullTimeSalaryStrategy());
            map.put(EmployeeType.CONTRACT, new ContractSalaryStrategy());
            map.put(EmployeeType.INTERN, new InternSalaryStrategy());
            this.strategies = Map.copyOf(map);
        }

        SalaryStrategy getStrategy(EmployeeType type) {
            SalaryStrategy strategy = strategies.get(Objects.requireNonNull(type));
            if (strategy == null) {
                throw new IllegalArgumentException("No salary strategy for: " + type);
            }
            return strategy;
        }
    }

    // -------------------- REPOSITORY (supporting abstraction) --------------------
    record Payroll(int employeeId, BigDecimal gross, BigDecimal tax, BigDecimal net) {
        Payroll {
            Objects.requireNonNull(gross);
            Objects.requireNonNull(tax);
            Objects.requireNonNull(net);
            if (employeeId <= 0 || gross.signum() < 0 || tax.signum() < 0
                    || gross.subtract(tax).compareTo(net) != 0) {
                throw new IllegalArgumentException("Invalid payroll record");
            }
        }
    }

    interface PayrollRepository {
        void save(Payroll payroll);
        Payroll findByEmployeeId(int employeeId);
    }

    static final class InMemoryPayrollRepository implements PayrollRepository {
        private final Map<Integer, Payroll> records = new HashMap<>();

        @Override
        public void save(Payroll payroll) {
            records.put(payroll.employeeId(), payroll);
            System.out.println("[Repository] Payroll saved for employee " + payroll.employeeId());
        }

        @Override
        public Payroll findByEmployeeId(int employeeId) {
            return records.get(employeeId);
        }
    }

    // -------------------- OBSERVER --------------------
    interface PayrollObserver {
        void onPayrollProcessed(Employee employee, Payroll payroll);
    }

    static final class PayslipEmailObserver implements PayrollObserver {
        @Override
        public void onPayrollProcessed(Employee employee, Payroll payroll) {
            System.out.println("[Observer] Payslip email simulated for " + employee.name());
        }
    }

    static final class PayrollAuditObserver implements PayrollObserver {
        @Override
        public void onPayrollProcessed(Employee employee, Payroll payroll) {
            System.out.println("[Observer] Audit event recorded for employee " + employee.id());
        }
    }

    // -------------------- BUSINESS SERVICE --------------------
    static final class PayrollService {
        private final EmployeeValidator validator;
        private final SalaryStrategyFactory strategyFactory;
        private final PayrollRepository repository;
        private final List<PayrollObserver> observers;

        PayrollService(EmployeeValidator validator, SalaryStrategyFactory strategyFactory,
                       PayrollRepository repository, List<PayrollObserver> observers) {
            this.validator = Objects.requireNonNull(validator);
            this.strategyFactory = Objects.requireNonNull(strategyFactory);
            this.repository = Objects.requireNonNull(repository);
            this.observers = List.copyOf(observers);
        }

        Payroll process(Employee employee) {
            validator.validate(employee);
            SalaryStrategy strategy = strategyFactory.getStrategy(employee.type());
            BigDecimal gross = strategy.calculateGrossSalary(employee);
            BigDecimal tax = gross.multiply(TAX_RATE);
            BigDecimal net = gross.subtract(tax);
            Payroll payroll = new Payroll(employee.id(), gross, tax, net);

            repository.save(payroll);
            for (PayrollObserver observer : observers) {
                try {
                    observer.onPayrollProcessed(employee, payroll);
                } catch (RuntimeException ex) {
                    // One observer failure should not prevent the other observers.
                    System.err.println("[Observer error] " + ex.getMessage());
                }
            }
            return payroll;
        }
    }

    // -------------------- COMMAND --------------------
    interface Command {
        void execute();
    }

    static final class ProcessPayrollCommand implements Command {
        private final PayrollService service;
        private final Employee employee;

        ProcessPayrollCommand(PayrollService service, Employee employee) {
            this.service = Objects.requireNonNull(service);
            this.employee = Objects.requireNonNull(employee);
        }

        @Override
        public void execute() {
            service.process(employee);
        }
    }

    // INVOKER: knows when to execute, not how payroll is calculated.
    static final class PayrollButton {
        private final Command command;

        PayrollButton(Command command) {
            this.command = Objects.requireNonNull(command);
        }

        void press() {
            System.out.println("[Invoker] Manager clicked Process Payroll");
            command.execute();
        }
    }
}
