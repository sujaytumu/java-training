package day_09_day_10.payroll.validation;

import day_09_day_10.payroll.model.Employee;

public final class ActiveEmployeeValidator extends BaseEmployeeValidator {
    @Override
    protected void check(Employee employee) {
        if (!employee.active()) throw new IllegalArgumentException("Employee is inactive");
    }
}
