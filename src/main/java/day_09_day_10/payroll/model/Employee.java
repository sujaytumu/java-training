package day_09_day_10.payroll.model;

import java.math.BigDecimal;
import java.util.Objects;

/** Immutable Employee whose construction is controlled by the Builder. */
public final class Employee {
    private final int id;
    private final String name;
    private final EmployeeType type;
    private final BigDecimal baseSalary;
    private final boolean active;

    private Employee(Builder builder) {
        id = builder.id;
        name = Objects.requireNonNull(builder.name, "name is required").trim();
        type = Objects.requireNonNull(builder.type, "employee type is required");
        baseSalary = Objects.requireNonNull(builder.baseSalary, "base salary is required");
        active = builder.active;

        if (id <= 0) throw new IllegalArgumentException("Employee id must be positive");
        if (name.isBlank()) throw new IllegalArgumentException("Employee name cannot be blank");
        if (baseSalary.signum() < 0) throw new IllegalArgumentException("Base salary cannot be negative");
    }

    public int id() { return id; }
    public String name() { return name; }
    public EmployeeType type() { return type; }
    public BigDecimal baseSalary() { return baseSalary; }
    public boolean active() { return active; }

    public static final class Builder {
        private int id;
        private String name;
        private EmployeeType type;
        private BigDecimal baseSalary;
        private boolean active = true;

        public Builder id(int value) { id = value; return this; }
        public Builder name(String value) { name = value; return this; }
        public Builder type(EmployeeType value) { type = value; return this; }
        public Builder baseSalary(BigDecimal value) { baseSalary = value; return this; }
        public Builder active(boolean value) { active = value; return this; }
        public Employee build() { return new Employee(this); }
    }
}
