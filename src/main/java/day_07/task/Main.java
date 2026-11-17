package day_07.task;

import day_02.model.*;
import day_07.service.PayrollService;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        // Runnable
        Runnable task = () -> System.out.println("Background task running");

        Thread thread = new Thread(task);

        // t lifecycle: NEW
        System.out.println("State: " + thread.getState());

        thread.start();
        thread.join();

        // t lifecycle: TERMINATED
        System.out.println("State: " + thread.getState());

        // Existing model classes reused
        Department department =
                new Department(1, "Engineering");

        Designation designation =
                new Designation(1, "Developer");

        // Employee test data
        List<Employee> employees = Arrays.asList(

                new Employee(101, "Rahul", "rahul@gmail.com",
                        40000, department, designation,
                        EmploymentType.FULL_TIME),

                new Employee(102, "Aman", "aman@gmail.com",
                        50000, department, designation,
                        EmploymentType.FULL_TIME),

                new Employee(103, "Priya", "priya@gmail.com",
                        45000, department, designation,
                        EmploymentType.FULL_TIME),

                new Employee(104, "Kiran", "kiran@gmail.com",
                        55000, department, designation,
                        EmploymentType.FULL_TIME),

                new Employee(105, "Arjun", "arjun@gmail.com",
                        60000, department, designation,
                        EmploymentType.FULL_TIME)
        );

        // Start payroll engine
        PayrollService service = new PayrollService();

        service.processPayroll(employees);
    }
}
