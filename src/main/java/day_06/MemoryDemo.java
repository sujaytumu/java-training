package day_06;

import day_02.model.Employee;
import day_02.model.Department;
import day_02.model.Designation;
import day_02.model.EmploymentType;

import java.lang.ref.WeakReference;

public class MemoryDemo {

    public static void run() {

        Employee employee = new Employee(
                106L, "Sujay", "sujay@gmail.com", 50000,
                new Department(1, "Engineering"),
                new Designation(1, "Software Developer"),
                EmploymentType.FULL_TIME
        );

        Employee sameEmployee = employee;

        System.out.println("Same object: "
                + (employee == sameEmployee));

        sameEmployee = null;

        System.out.println("Employee still reachable: "
                + (employee != null));

        WeakReference<Employee> weak =
                new WeakReference<>(employee);

        employee = null;

        System.gc();

        System.out.println("Weak reference after GC request: "
                + weak.get());
    }
}
