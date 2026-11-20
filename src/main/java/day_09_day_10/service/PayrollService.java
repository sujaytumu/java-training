package day_09_day_10.service;
import day_09_day_10.model.*;
import day_09_day_10.factory.SalaryStrategyFactory;
import day_09_day_10.repository.PayrollRepository;
import day_09_day_10.observer.PayrollObserver;
import day_09_day_10.specification.Specification;
import day_09_day_10.validation.PayrollValidator;
import java.util.List;
public class PayrollService {
 private final PayrollRepository repository; private final Specification<Employee> eligibility; private final List<PayrollObserver> observers; private final PayrollValidator validator;
 public PayrollService(PayrollRepository repository,Specification<Employee> eligibility,List<PayrollObserver> observers,PayrollValidator validator){
 this.repository=repository;this.eligibility=eligibility;this.observers=List.copyOf(observers);this.validator=validator;
 }
 public Payroll process(Employee employee){
 validator.validate(employee);
 if(!eligibility.isSatisfiedBy(employee))throw new IllegalArgumentException("Employee not eligible");
 double gross=SalaryStrategyFactory.getStrategy(employee.getType()).calculateGrossSalary(employee);
 double tax=gross*0.10;
 Payroll payroll=new Payroll(employee.getId(),gross,tax,gross-tax);
 repository.save(payroll);
 for(PayrollObserver observer:observers)observer.onPayrollProcessed(employee,payroll);
 return payroll;
 }
}
