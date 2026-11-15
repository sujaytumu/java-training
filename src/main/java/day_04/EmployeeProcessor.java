
package day_04;

import day_02.model.Employee;

@FunctionalInterface
public interface EmployeeProcessor {
    void process(Employee employee);
}
