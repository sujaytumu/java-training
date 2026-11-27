package day_09_day_10.payroll;

import day_09_day_10.payroll.abstractfactory.PayrollComponentFactory;
import day_09_day_10.payroll.abstractfactory.StandardPayrollComponentFactory;
import day_09_day_10.payroll.factory.SalaryStrategyFactory;
import day_09_day_10.payroll.model.Employee;
import day_09_day_10.payroll.model.EmployeeType;
import day_09_day_10.payroll.observer.AuditObserver;
import day_09_day_10.payroll.observer.EmailObserver;
import day_09_day_10.payroll.observer.PayrollObserver;
import day_09_day_10.payroll.repository.InMemoryPayrollRepository;
import day_09_day_10.payroll.repository.PayrollRepository;
import day_09_day_10.payroll.service.PayrollService;
import day_09_day_10.payroll.validation.ActiveEmployeeValidator;
import day_09_day_10.payroll.validation.EmployeeValidator;
import day_09_day_10.payroll.validation.SalaryValidator;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // BUILDER: construct a valid immutable employee.
        Employee employee = new Employee.Builder()
                .id(101)
                .name("Sujay")
                .type(EmployeeType.FULL_TIME)//contract
                .baseSalary(new BigDecimal("30000.00"))
                .active(true)
                .build();

        // CHAIN OF RESPONSIBILITY: active check -> salary check.
        EmployeeValidator validationChain = new ActiveEmployeeValidator();
        validationChain.setNext(new SalaryValidator());

        // Wire the application's collaborators at the entry point.
        PayrollRepository repository = new InMemoryPayrollRepository();
        SalaryStrategyFactory strategyFactory = new SalaryStrategyFactory();

        // ABSTRACT FACTORY: create a consistent family of payroll components.
        PayrollComponentFactory componentFactory = new StandardPayrollComponentFactory();
        //new Alternatepayroll...

        // OBSERVER: listeners are independent of the payroll business logic.
        //list creates 2 objs(email,auditmsg) both implements payrollObserver
        List<PayrollObserver> observers = List.of(new EmailObserver(), new AuditObserver());
        //push-style Observer pattern: the publisher passes
        //the employee and payroll to each observer.
        PayrollService service = new PayrollService(
                validationChain, strategyFactory, componentFactory, repository, observers);

        // Run the complete business use case.
        var payroll = service.process(employee);
        System.out.println("\nFinal payslip:");
        System.out.println(componentFactory.payslipFormatter().format(employee, payroll));
        //101 done here.

        // -ve path test: inactive employee must be rejected before save/notify.
        Employee inactive = new Employee.Builder()
                .id(102)
                .name("Inactive Employee")
                .type(EmployeeType.CONTRACT)//full-time
                .baseSalary(new BigDecimal("25000.00"))
                .active(false)//observer....true
                //inactive no salary etc, direct rejection
                .build();

        try {
            service.process(inactive);
        } catch (IllegalArgumentException exception) {
            System.out.println("\nExpected rejection: " + exception.getMessage());
        }
    }
}
