package day_09_day_10.observer;
import day_09_day_10.model.*;
public class NotificationObserver implements PayrollObserver { public void onPayrollProcessed(Employee e,Payroll p){System.out.println("Notification generated for "+e.getName());} }
