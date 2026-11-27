package day_09_day_10.payroll.validation;

import day_09_day_10.payroll.model.Employee;

public final class ActiveEmployeeValidator extends BaseEmployeeValidator {
    @Override
    //cor
    protected void check(Employee employee) {
        //active
        if (!employee.active()) throw new IllegalArgumentException("Employee is inactive");
    }
}
