package day_09_day_10.payroll.validation;

import day_09_day_10.payroll.model.Employee;
import day_09_day_10.payroll.model.EmployeeType;

public final class SalaryValidator extends BaseEmployeeValidator {
    @Override
    protected void check(Employee employee) {
        //salary
        //Non-intern employees basesal>0
        if (employee.type() != EmployeeType.INTERN && employee.baseSalary().signum() <= 0) {
            throw new IllegalArgumentException("Base salary must be greater than zero");
        }//stops if thrown
    }
}
