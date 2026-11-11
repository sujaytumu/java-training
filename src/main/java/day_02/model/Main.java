
package day_02;

import day_02.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Creating departments
        Department engineering = new Department(1, "Engineering");
        Department humanResources = new Department(2, "Human Resources");

        // Creating designations
        Designation softwareEngineer =
                new Designation(101, "Software Engineer");

        Designation hrExecutive =
                new Designation(102, "HR Executive");

        // Creating employees
        Employee employee1 = new Employee(
                103, "Sujay", "sujay@gmail.com",
                45000, engineering, softwareEngineer,
                EmploymentType.FULL_TIME
        );

        Employee employee2 = new Employee(
                101, "Rahul", "rahul@gmail.com",
                35000, engineering, softwareEngineer,
                EmploymentType.CONTRACT
        );

        Employee employee3 = new Employee(
                102, "Priya", "priya@gmail.com",
                40000, humanResources, hrExecutive,
                EmploymentType.INTERN
        );

        // Display employees
        System.out.println("EMPLOYEE DETAILS");

        System.out.println(employee1);
        System.out.println(employee2);
        System.out.println(employee3);

        // Static method
        System.out.println("\nTotal Employees: "
                + Employee.getEmployeeCount());

        // Salary update
        employee1.updateSalary(50000);

        System.out.println("\nUpdated Salary: "
                + employee1.getSalary());

        // Comparable - Sort by employee ID
        List<Employee> employees = new ArrayList<>();

        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);

        Collections.sort(employees);

        System.out.println("\nSORTED BY EMPLOYEE ID");

        for (Employee employee : employees) {
            System.out.println(employee.getEmployeeId()
                    + " - " + employee.getName());
        }

        // Comparator - Sort by salary
        employees.sort(
                Comparator.comparingDouble(Employee::getSalary)
        );

        System.out.println("\nSORTED BY SALARY");

        for (Employee employee : employees) {
            System.out.println(employee.getName()
                    + " - " + employee.getSalary());
        }

        // Object equality
        Employee duplicateEmployee = new Employee(
                103, "Sujay", "another@gmail.com",
                60000, engineering, softwareEngineer,
                EmploymentType.FULL_TIME
        );

        System.out.println("\nOBJECT EQUALITY");

        System.out.println(employee1.equals(duplicateEmployee));

        System.out.println("HashCode 1: "
                + employee1.hashCode());

        System.out.println("HashCode 2: "
                + duplicateEmployee.hashCode());
    }
}
