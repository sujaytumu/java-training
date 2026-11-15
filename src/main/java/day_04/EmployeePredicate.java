package day_04;

import day_02.model.Employee;

@FunctionalInterface
public interface EmployeePredicate {
    boolean test(Employee employee);
}
