
package day_07.service;

import day_02.model.Employee;
import day_07.task.PayrollTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class PayrollService {

    // Reusable worker threads
    private final ExecutorService executor =
            Executors.newFixedThreadPool(3);

    private final PayrollLedger ledger = new PayrollLedger();

    public void processPayroll(List<Employee> employees)
            throws Exception {

        List<Future<Double>> results = new ArrayList<>();

        try {
            // Submit all employee tasks
            for (Employee employee : employees) {
                results.add(
                        executor.submit(new PayrollTask(employee))
                        //new Thread(new PayrollTask(employee)).start()..runnable
                );
            }

            // Collect calculated salaries
            for (int i = 0; i < employees.size(); i++) {

                double salary = results.get(i).get();

                // Store consistent records
                ledger.recordSalary(
                        employees.get(i).getEmployeeId(), salary
                );
            }

            // Async report generation
            CompletableFuture<Void> report =
                    CompletableFuture.runAsync(ledger::displayReport);

            report.get();

        } finally {
            // Graceful shutdown
            executor.shutdown();

            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        }
    }
}
