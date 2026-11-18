package day_05.validator;

import day_02.model.Employee;
import day_05.exception.EmployeeException.InvalidEmployeeException;

public class EmployeeValidator {

    public static void validate(Employee employee) {

        //check employee == null before calling employee.getEmployeeId()...calling null==NullPointerExc.
        if (employee == null)
            throw new InvalidEmployeeException("Employee cannot be null");

        if (employee.getEmployeeId() <= 0)
            throw new InvalidEmployeeException("Invalid employee ID");

        //throws NullPointerException
        // || short-circuit evaluation(keep &&)...don't call isBlank() if null is True
        if (employee.getName() == null || employee.getName().isBlank())
            throw new InvalidEmployeeException("Invalid employee name");

        if (employee.getSalary() <= 0)
            throw new InvalidEmployeeException("Invalid employee salary");
    }
}