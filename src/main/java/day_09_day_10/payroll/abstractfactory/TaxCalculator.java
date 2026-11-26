package day_09_day_10.payroll.abstractfactory;

import java.math.BigDecimal;

public interface TaxCalculator {
    BigDecimal calculateTax(BigDecimal grossSalary);
}
