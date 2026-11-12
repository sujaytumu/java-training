
package day_03;

import day_02.model.*;
import day_03.repository.EmployeeRepository;
import day_03.service.EmployeeService;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        EmployeeRepository repository = new EmployeeRepository();

        EmployeeService service = new EmployeeService(repository);

        // Add employees
        Employee employee1 = new Employee(
                101L, "Sujay", "sujay@gmail.com", 50000,
                new Department(1, "Engineering"),
                new Designation(1, "Software Developer"),
                EmploymentType.FULL_TIME
        );
        //Employee employee1 = null; // Replace with your Employee constructor
        Employee employee2 = new Employee(
                102L, "Rahul", "rahul@gmail.com", 65000,
                new Department(1, "Engineering"),
                new Designation(2, "Senior Developer"),
                EmploymentType.FULL_TIME
        );
        //
        Employee employee3 = new Employee(
                103L, "Arjun", "arjun@gmail.com", 40000,
                new Department(2, "HR"),
                new Designation(3, "HR Executive"),
                EmploymentType.CONTRACT
        );

        // Add actual employee objects
        repository.addEmployee(employee1);
        repository.addEmployee(employee2);
        repository.addEmployee(employee3);

        // Search employee
        System.out.println("Search: " + repository.findById(101L));

        // Duplicate detection
        System.out.println("Duplicate added: " +
                repository.addEmployee(employee1));

        // Filter by salary
        System.out.println("\nSalary Filter:");
        System.out.println(service.filterBySalary(40000, 70000));

        // Sort by name
        System.out.println("\nSort by Name:");
        System.out.println(service.sortByName());

        // Sort by salary
        System.out.println("\nSort by Salary:");
        System.out.println(service.sortBySalary());

        // Group by department
        System.out.println("\nDepartment Grouping:");
        System.out.println(service.groupByDepartment());

        // Department-wise salary
        System.out.println("\nDepartment Salary:");
        System.out.println(service.calculateSalaryByDepartment());

        // Highest-paid employee
        System.out.println("\nHighest Paid:");
        System.out.println(service.findHighestPaid(repository.findAll()));

        // Queue processing
        System.out.println("\nNext Pending Employee:");
        System.out.println(repository.processNextEmployee());

        // ConcurrentHashMap
        service.cacheEmployee(employee1);
        System.out.println("\nCached Employee:");
        System.out.println(service.getCachedEmployee(employee1.getEmployeeId()));

        // Immutable collection
        List<Employee> immutableEmployees = repository.findAll();
        System.out.println("\nImmutable Employee List:");
        System.out.println(immutableEmployees);
    }
}
