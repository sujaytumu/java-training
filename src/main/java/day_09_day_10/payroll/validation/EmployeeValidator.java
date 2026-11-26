package day_09_day_10.payroll.validation;

import day_09_day_10.payroll.model.Employee;

public interface EmployeeValidator {
    void validate(Employee employee);
    EmployeeValidator setNext(EmployeeValidator next);
}
