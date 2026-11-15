package day_04;

import day_02.model.Employee;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class FunctionalDemo {

    public static void run(List<Employee> employees) {

        // 1. Lambda Expression
        Runnable task = () -> System.out.println("Processing Employees");
        task.run();

        // 2. Predicate - Boolean condition
        Predicate<Employee> highSalary =
                e -> e.getSalary() > 50000;

        Predicate<Employee> validName =
                e -> e.getName() != null;

        // 3. Function - Transformation
        Function<Employee, String> getName =
                Employee::getName;

        // 4. Consumer - Action
        Consumer<Employee> display =
                e -> System.out.println(e.getName());

        // 5. Supplier - Value generation
        Supplier<String> message =
                () -> "Employee Report Generated";

        System.out.println(message.get());

        // 6. Custom Boolean Functional Interface
        EmployeePredicate eligible =
                e -> e.getSalary() > 40000 && e.getName() != null;

        // 7. Custom Functional Interface
        EmployeeProcessor processor =
                e -> System.out.println("Processing: " + e.getName());

        employees.stream()
                .filter(eligible::test)
                .forEach(processor::process);

        // 8. Method Reference
        employees.stream()
                .map(Employee::getName)
                .forEach(System.out::println);

        // 9. Filter + Map
        List<String> names = employees.stream()
                .filter(highSalary.and(validName))
                .map(getName)
                .collect(Collectors.toList());

        System.out.println("High Salary: " + names);

        // 10. Sorting
        employees.stream()
                .sorted(Comparator.comparingDouble(
                        Employee::getSalary).reversed())
                .forEach(display);

        // 11. FlatMap
        List<List<String>> skills = List.of(
                List.of("Java", "SQL"),
                List.of("Spring", "React")
        );

        List<String> allSkills = skills.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        System.out.println(allSkills);

        // 12. Reduce
        double totalSalary = employees.stream()
                .map(Employee::getSalary)
                .reduce(0.0, Double::sum);

        System.out.println("Total Salary: " + totalSalary);

        // 13. Collectors + GroupingBy
        Map<Object, Long> departmentCount = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.counting()));

        System.out.println(departmentCount);

        // 14. Optional
        Optional<Employee> highestPaid = employees.stream()
                .max(Comparator.comparingDouble(
                        Employee::getSalary));

        highestPaid.ifPresent(System.out::println);

        // 15. Parallel Stream
        long count = employees.parallelStream()
                .filter(highSalary)
                .count();

        System.out.println("Parallel Count: " + count);
    }
}
