package day_09_day_10.payroll.strategy;

import day_09_day_10.payroll.model.Employee;
import java.math.BigDecimal;

public final class InternSalaryStrategy implements SalaryStrategy {
    @Override
    public BigDecimal calculateGrossSalary(Employee employee) {
        return new BigDecimal("15000.00");
    }
}
