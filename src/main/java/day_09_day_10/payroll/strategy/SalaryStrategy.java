package day_09_day_10.payroll.strategy;

import day_09_day_10.payroll.model.Employee;
import java.math.BigDecimal;

public interface SalaryStrategy {
    //salary differs for each  job role
    BigDecimal calculateGrossSalary(Employee employee);
}
