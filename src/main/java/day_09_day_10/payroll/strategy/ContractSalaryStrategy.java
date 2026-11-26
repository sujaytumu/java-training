package day_09_day_10.payroll.strategy;

import day_09_day_10.payroll.model.Employee;
import java.math.BigDecimal;

public final class ContractSalaryStrategy implements SalaryStrategy {
    @Override
    public BigDecimal calculateGrossSalary(Employee employee) {
        return employee.baseSalary().multiply(new BigDecimal("1.20"));
    }
}
