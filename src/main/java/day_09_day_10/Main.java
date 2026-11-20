package day_09_day_10;
import day_09_day_10.model.*;
import day_09_day_10.repository.*;
import day_09_day_10.observer.*;
import day_09_day_10.service.*;
import day_09_day_10.specification.*;
import day_09_day_10.validation.*;
import day_09_day_10.command.*;
import day_09_day_10.state.*;
import java.util.List;
public class Main {
 public static void main(String[] args){
 Employee employee=new Employee.Builder().id(101).name("Sujay").type(EmployeeType.FULL_TIME).basicSalary(30000).active(true).build();
 PayrollRepository repository=new InMemoryPayrollRepository();
 PayrollValidator chain=new ActiveValidator();chain.setNext(new SalaryValidator());
 PayrollService service=new PayrollService(repository,new ActiveEmployeeSpecification(),List.of(new EmailObserver(),new NotificationObserver()),chain);
 Payroll payroll=service.process(employee);
 System.out.println("Gross Salary: "+payroll.grossSalary());
 System.out.println("Tax: "+payroll.tax());
 System.out.println("Net Salary: "+payroll.netSalary());
 new ProcessPayrollCommand(service,new Employee.Builder().id(102).name("Intern").type(EmployeeType.INTERN).basicSalary(0).build()).execute();
 PayrollWorkflow workflow=new PayrollWorkflow();workflow.approve();workflow.start();workflow.complete();
 System.out.println("Workflow status: "+workflow.status());
 }
}
