package day_09_day_10.payroll.observer;

import day_09_day_10.payroll.model.Employee;
import day_09_day_10.payroll.model.Payroll;

public final class AuditObserver implements PayrollObserver {
    @Override
    public void onPayrollProcessed(Employee employee, Payroll payroll) {
        System.out.println("[AUDIT] Saved employeeId=" + employee.id() + ", netPay=" + payroll.net());
    }
}
