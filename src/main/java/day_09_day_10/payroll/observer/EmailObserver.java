package day_09_day_10.payroll.observer;

import day_09_day_10.payroll.model.Employee;
import day_09_day_10.payroll.model.Payroll;

public final class EmailObserver implements PayrollObserver {
    @Override
    public void onPayrollProcessed(Employee employee, Payroll payroll) {
        System.out.println("[EMAIL] Payslip notification prepared for " + employee.name());
    }
}
