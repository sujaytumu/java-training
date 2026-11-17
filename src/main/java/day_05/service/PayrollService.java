package day_05.service;

import day_02.model.Employee;
import day_03.repository.EmployeeRepository;
import day_05.validator.EmployeeValidator;
import day_05.exception.EmployeeException.EmployeeNotFoundException;

import java.util.*;
import java.util.logging.Logger;

public class PayrollService {

    private static final Logger logger =
            Logger.getLogger(PayrollService.class.getName());

    private final EmployeeRepository repository;

    public PayrollService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<String> generatePayroll() {

        List<Employee> employees = repository.findAll();
        List<String> report = new ArrayList<>();

        if (employees.isEmpty())
            throw new EmployeeNotFoundException("No employees available");

        for (Employee employee : employees) {

            EmployeeValidator.validate(employee);

            double salary = employee.getSalary();
            double bonus = salary * 0.10;
            double tax = salary * 0.10;
            double netSalary = salary + bonus - tax;

            report.add(
                    employee.getEmployeeId() + " | " +
                            employee.getName() + " | " +
                            salary + " | " +
                            bonus + " | " +
                            tax + " | " +
                            netSalary
            );

            logger.info("Payroll generated for " + employee.getName());
        }

        return report;
    }
}