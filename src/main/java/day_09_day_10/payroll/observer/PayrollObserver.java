package day_09_day_10.payroll.observer;

import day_09_day_10.payroll.model.Employee;
import day_09_day_10.payroll.model.Payroll;

//event callback
public interface PayrollObserver {
    void onPayrollProcessed(Employee employee, Payroll payroll);
}
