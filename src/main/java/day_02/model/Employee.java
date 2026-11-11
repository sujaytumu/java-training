
package day_02.model;

import java.util.Objects;

public class Employee implements Comparable<Employee> {

    private static int employeeCount = 0;

    private final long employeeId;
    private final String name;
    private final String email;

    private double salary;

    private final Department department;
    private final Designation designation;
    private final EmploymentType employmentType;

    public Employee(long employeeId, String name, String email,
                    double salary, Department department,
                    Designation designation, EmploymentType employmentType) {

        if (employeeId <= 0) {
            throw new IllegalArgumentException("Invalid employee ID");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Employee name cannot be empty");
        }

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        if (salary <= 0) {
            throw new IllegalArgumentException("Salary must be positive");
        }

        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
        this.salary = salary;
        this.department = Objects.requireNonNull(department);
        this.designation = Objects.requireNonNull(designation);
        this.employmentType = Objects.requireNonNull(employmentType);

        employeeCount++;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public double getSalary() {
        return salary;
    }

    public Department getDepartment() {
        return department;
    }

    public Designation getDesignation() {
        return designation;
    }

    public EmploymentType getEmploymentType() {
        return employmentType;
    }

    public static int getEmployeeCount() {
        return employeeCount;
    }

    public void updateSalary(double salary) {
        if (salary <= 0) {
            throw new IllegalArgumentException("Salary must be positive");
        }

        this.salary = salary;
    }

    @Override
    public int compareTo(Employee other) {
        return Long.compare(this.employeeId, other.employeeId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Employee)) {
            return false;
        }

        Employee other = (Employee) obj;

        return employeeId == other.employeeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId);
    }

    @Override
    public String toString() {
        return employeeId + " " + name + " " + email + " " + salary;
    }
}
