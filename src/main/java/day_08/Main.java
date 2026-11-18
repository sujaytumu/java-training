package day_08;

import day_08.service.EmployeeIntegrationService;

import java.util.List;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws Exception {

        System.out.println("Day_08 work");
        EmployeeIntegrationService service =
                new EmployeeIntegrationService();

        List<Integer> employeeIds =
                IntStream.rangeClosed(101, 130)
                        .boxed()
                        .toList();

        System.out.println("EMPLOYEE DATA INTEGRATION SERVICE");

        System.out.println("Total employees: " + employeeIds.size());

        service.runWithPlatformThreads(employeeIds);

        service.runWithVirtualThreads(employeeIds);
    }
}