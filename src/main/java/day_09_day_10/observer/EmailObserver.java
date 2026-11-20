package day_09_day_10.observer;
import day_09_day_10.model.*;
public class EmailObserver implements PayrollObserver { public void onPayrollProcessed(Employee e,Payroll p){System.out.println("Payslip email simulated for "+e.getName());} }
