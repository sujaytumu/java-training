
package day_07.task;

import day_02.model.Employee;
import java.util.concurrent.Callable;

public class PayrollTask implements Callable<Double> {

    private final Employee employee;

    public PayrollTask(Employee employee) {
        this.employee = employee;
    }

    @Override
    public Double call() throws Exception {

        // Worker identification
        System.out.println(Thread.currentThread().getName()
                + " processing " + employee.getName());

        // Simulates processing delay
        Thread.sleep(1000);

        double salary = employee.getSalary();

        // Business calculation
        double bonus = salary * 0.05;

        // Callable returns result
        return salary + bonus;
    }
}
