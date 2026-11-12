
package day_03.service;

import day_02.model.Employee;
import day_03.repository.EmployeeRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class EmployeeService {

    private final EmployeeRepository repository;

    // Thread-safe employee cache
    private final Map<Long, Employee> employeeCache =
            new ConcurrentHashMap<>();

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    // Filter employees by salary range
    public List<Employee> filterBySalary(double min, double max) {

        List<Employee> result = new ArrayList<>();

        for (Employee employee : repository.findAll()) {

            if (employee.getSalary() >= min &&
                    employee.getSalary() <= max) {

                result.add(employee);
            }
        }

        return result;
    }

    // Natural sorting using Comparable
    public List<Employee> sortByName() {

        List<Employee> result = new ArrayList<>(repository.findAll());

        Collections.sort(result);

        return result;
    }

    // Custom sorting using Comparator
    public List<Employee> sortBySalary() {

        List<Employee> result = new ArrayList<>(repository.findAll());

        result.sort(
                Comparator.comparingDouble(Employee::getSalary).reversed()
        );

        return result;
    }

    // Group employees by department
    public Map<String, List<Employee>> groupByDepartment() {

        Map<String, List<Employee>> result = new TreeMap<>();

        for (Employee employee : repository.findAll()) {

            String department = String.valueOf(employee.getDepartment());

            result.computeIfAbsent(
                    department,
                    key -> new ArrayList<>()
            ).add(employee);
        }

        return result;
    }

    // Calculate department-wise salary
    public Map<String, Double> calculateSalaryByDepartment() {

        Map<String, Double> result = new TreeMap<>();

        for (Employee employee : repository.findAll()) {

            String department = String.valueOf(employee.getDepartment());

            result.merge(
                    department,
                    employee.getSalary(),
                    Double::sum
            );
        }

        return result;
    }

    // Find highest-paid employee using bounded generics
    public <T extends Employee> T findHighestPaid(List<T> employees) {

        T highest = null;

        for (T employee : employees) {

            if (highest == null ||
                    employee.getSalary() > highest.getSalary()) {

                highest = employee;
            }
        }

        return highest;
    }

    // PECS: Producer Extends, Consumer Super
    public void copyEmployees(
            List<? extends Employee> source,
            List<? super Employee> destination) {

        destination.addAll(source);
    }

    // ConcurrentHashMap operations
    public void cacheEmployee(Employee employee) {
        employeeCache.put(employee.getEmployeeId(), employee);
    }

    public Employee getCachedEmployee(long employeeId) {
        return employeeCache.get(employeeId);
    }
}
