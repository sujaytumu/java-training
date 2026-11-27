package day_09_day_10.payroll.abstractfactory;

import java.math.BigDecimal;

public interface TaxCalculator {
    //what tax format is available..(10%/15%0
    BigDecimal calculateTax(BigDecimal grossSalary);
}
