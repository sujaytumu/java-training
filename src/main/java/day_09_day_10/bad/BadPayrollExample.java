package day_09_day_10.bad;
import day_09_day_10.model.*;
public class BadPayrollExample {
 public void process(Employee e){
 if(!e.isActive())throw new IllegalArgumentException("Inactive");
 double gross=0;
 if(e.getType()==EmployeeType.FULL_TIME)gross=e.getBasicSalary()+10000;
 else if(e.getType()==EmployeeType.INTERN)gross=15000;
 else if(e.getType()==EmployeeType.CONTRACT)gross=e.getBasicSalary()*1.2;
 System.out.println("Net salary: "+(gross-gross*0.10));
 System.out.println("Email sent (simulated) to "+e.getName());
 // Bad design: validation, algorithms and notifications all in one class.
 }
}
