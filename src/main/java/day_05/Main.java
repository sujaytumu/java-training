
package day_05;

import day_02.model.*;
import day_03.repository.EmployeeRepository;
import day_05.service.PayrollService;
import day_05.service.PayrollReportService;
import day_05.exception.EmployeeException;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger =
            Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        // Repository
        EmployeeRepository repository = new EmployeeRepository();

        // Departments
        Department engineering = new Department(1, "Engineering");
        Department hr = new Department(2, "HR");

        // Designations
        Designation developer = new Designation(1, "Developer");
        Designation manager = new Designation(2, "Manager");

        // Add employees
        repository.addEmployee(new Employee(
                101, "Sujay", "sujay@gmail.com", 50000,
                engineering, developer, EmploymentType.FULL_TIME
        ));

        repository.addEmployee(new Employee(
                102, "Surya", "surya@gmail.com", 60000,
                hr, manager, EmploymentType.FULL_TIME
        ));

        repository.addEmployee(new Employee(
                103, "Navya", "navya@gmail.com", 40000,
                engineering, developer, EmploymentType.CONTRACT
        ));

        // Services
        PayrollService payrollService = new PayrollService(repository);
        PayrollReportService reportService = new PayrollReportService();

        try {
            List<String> records = payrollService.generatePayroll();

            reportService.saveReport(records);

            System.out.println("\nPayroll generated successfully!");
            System.out.println("Total employees: " + records.size());
            System.out.println("Report: payroll-report.txt");

            records.forEach(System.out::println);

        } catch (EmployeeException e) {//varying warning logger vs severe logger
            logger.warning("Payroll processing failed: " + e.getMessage());

        } catch (IOException e) {
            logger.severe("Report generation failed: " + e.getMessage());
        }
    }
}
