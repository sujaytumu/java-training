package day_09_day_10.payroll.abstractfactory;

import day_09_day_10.payroll.model.Employee;
import day_09_day_10.payroll.model.Payroll;

public interface PayslipFormatter {
    String format(Employee employee, Payroll payroll);
}
