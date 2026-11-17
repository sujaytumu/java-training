
package day_07.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class PayrollLedger {

    // Thread-safe employee records
    private final ConcurrentHashMap<Long, Double> records =
            new ConcurrentHashMap<>();

    // Atomic counter
    private final AtomicInteger processedEmployees =
            new AtomicInteger(0);

    // Explicit lock
    private final ReentrantLock lock = new ReentrantLock();

    private double totalPayroll = 0;

    public void recordSalary(long employeeId, double salary) {

        lock.lock();

        try {
            // Critical section
            records.put(employeeId, salary);
            totalPayroll += salary;

            // Safe increment
            processedEmployees.incrementAndGet();

        } finally {
            // Always release lock
            lock.unlock();
        }
    }

    public void displayReport() {

        System.out.println("\n--- PAYROLL REPORT ---");

        records.forEach((id, salary) ->
                System.out.println(id + " : " + salary));

        System.out.println("Processed: "
                + processedEmployees.get());

        System.out.println("Total Payroll: " + totalPayroll);
    }
}
