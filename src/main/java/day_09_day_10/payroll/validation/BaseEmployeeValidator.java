package day_09_day_10.payroll.validation;

import day_09_day_10.payroll.model.Employee;
import java.util.Objects;

public abstract class BaseEmployeeValidator implements EmployeeValidator {
    private EmployeeValidator next;

    @Override
    public EmployeeValidator setNext(EmployeeValidator next) {
        this.next = Objects.requireNonNull(next);
        return next;
    }

    @Override
    public final void validate(Employee employee) {
        Objects.requireNonNull(employee, "employee is required");
        check(employee);
        if (next != null) next.validate(employee);
    }

    protected abstract void check(Employee employee);
}
