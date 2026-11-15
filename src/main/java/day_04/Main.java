package day_04;

import day_02.model.Employee;
import day_03.repository.EmployeeRepository;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        EmployeeRepository repository = new EmployeeRepository();

        List<Employee> employees = repository.findAll();

        FunctionalDemo.run(employees);
    }
}
