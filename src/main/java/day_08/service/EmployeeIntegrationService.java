package day_08.service;

import day_08.model.EmployeeIntegrationResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class EmployeeIntegrationService {

    private final AtomicInteger activeTasks = new AtomicInteger(0);
    private final AtomicInteger peakTasks = new AtomicInteger(0);

    private EmployeeIntegrationResult integrateEmployee(int employeeId)
            throws InterruptedException {

        int active = activeTasks.incrementAndGet();

        peakTasks.accumulateAndGet(active, Math::max);

        try {
            String name = fetchEmployeeFromHR(employeeId);

            double salary = fetchSalaryFromPayroll(employeeId);

            int attendance = fetchAttendance(employeeId);

            return new EmployeeIntegrationResult(
                    employeeId, name, salary, attendance
            );

        } finally {
            activeTasks.decrementAndGet();
        }
    }

    private String fetchEmployeeFromHR(int employeeId)
            throws InterruptedException {

        Thread.sleep(100);

        return "Employee-" + employeeId;
    }

    private double fetchSalaryFromPayroll(int employeeId)
            throws InterruptedException {

        Thread.sleep(100);

        return 40000 + (employeeId * 100);
    }

    private int fetchAttendance(int employeeId)
            throws InterruptedException {

        Thread.sleep(100);

        return 24;
    }

    public void runWithPlatformThreads(List<Integer> employeeIds)
            throws Exception {

        System.out.println("\n--- PLATFORM THREAD EXECUTION ---");

        activeTasks.set(0);
        peakTasks.set(0);

        ExecutorService executor =
                Executors.newFixedThreadPool(10);

        executeIntegration(executor, employeeIds);
    }

    public void runWithVirtualThreads(List<Integer> employeeIds)
            throws Exception {

        System.out.println("\n--- VIRTUAL THREAD EXECUTION ---");

        activeTasks.set(0);
        peakTasks.set(0);

        ExecutorService executor =
                Executors.newVirtualThreadPerTaskExecutor();

        executeIntegration(executor, employeeIds);
    }

    private void executeIntegration(
            ExecutorService executor,
            List<Integer> employeeIds
    ) throws Exception {

        long startTime = System.nanoTime();

        try (executor) {

            List<Callable<EmployeeIntegrationResult>> tasks =
                    new ArrayList<>();

            for (int employeeId : employeeIds) {

                tasks.add(() -> integrateEmployee(employeeId));
            }

            List<Future<EmployeeIntegrationResult>> futures =
                    executor.invokeAll(tasks);

            List<EmployeeIntegrationResult> results =
                    new ArrayList<>();

            for (Future<EmployeeIntegrationResult> future : futures) {

                results.add(future.get());
            }

            long endTime = System.nanoTime();

            long duration =
                    TimeUnit.NANOSECONDS.toMillis(endTime - startTime);

            System.out.println("Employees processed: " + results.size());

            System.out.println("Peak concurrent tasks: " + peakTasks.get());

            System.out.println("Execution time: " + duration + " ms");

            System.out.println("\nSample integrated employee:");

            System.out.println(results.get(0));
        }
    }
}